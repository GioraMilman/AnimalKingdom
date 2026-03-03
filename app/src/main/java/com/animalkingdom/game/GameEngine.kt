package com.animalkingdom.game

import kotlin.random.Random

class GameEngine(
    private val random: Random = Random.Default
) {
    private val animals = listOf(
        "bear_",
        "buffalo_",
        "dalmatian_dog",
        "donkey_",
        "elephant_",
        "flamingo_",
        "fox_",
        "frog_",
        "giraffe_",
        "hippopotamus_",
        "meerkat_",
        "orange_cat",
        "owl_",
        "panda_",
        "parrot_",
        "penguin_",
        "rabbit_",
        "raccoon_",
        "squirrel_",
        "tiger_",
        "turtle_",
        "warthog_",
        "wild_boar",
        "wolf_",
        "zebra_"
    )

    fun initBoard(difficulty: Difficulty): BoardState {
        val chosenAnimals = animals.shuffled(random).take(difficulty.pairCount)
        val cards = chosenAnimals.flatMapIndexed { index, animalName ->
            listOf(
                CardState(id = index, animalName = animalName),
                CardState(id = index, animalName = animalName)
            )
        }.shuffled(random)

        return BoardState(
            difficulty = difficulty,
            cards = cards
        )
    }

    fun tapCard(state: BoardState, index: Int): BoardState {
        if (index !in state.cards.indices) return state

        val target = state.cards[index]
        if (target.isMatched || target.isFaceUp) return state

        val faceUpUnmatched = state.cards.withIndex()
            .filter { (_, card) -> card.isFaceUp && !card.isMatched }

        if (faceUpUnmatched.size >= 2) return state

        val updatedCards = state.cards.toMutableList()
        updatedCards[index] = target.copy(isFaceUp = true)

        val nowFaceUp = updatedCards.withIndex().filter { (_, card) -> card.isFaceUp && !card.isMatched }
        if (nowFaceUp.size < 2) {
            return state.copy(cards = updatedCards)
        }

        val (first, second) = nowFaceUp
        val firstCard = first.value
        val secondCard = second.value

        return if (firstCard.id == secondCard.id) {
            updatedCards[first.index] = firstCard.copy(isFaceUp = true, isMatched = true)
            updatedCards[second.index] = secondCard.copy(isFaceUp = true, isMatched = true)
            val matches = state.matchesFound + 1
            val complete = matches == state.difficulty.pairCount
            state.copy(
                cards = updatedCards,
                moves = state.moves + 1,
                matchesFound = matches,
                completed = complete
            )
        } else {
            state.copy(cards = updatedCards, moves = state.moves + 1)
        }
    }

    fun hideUnmatchedFaceUp(state: BoardState): BoardState {
        val newCards = state.cards.map { card ->
            if (card.isFaceUp && !card.isMatched) card.copy(isFaceUp = false) else card
        }
        return state.copy(cards = newCards)
    }
}
