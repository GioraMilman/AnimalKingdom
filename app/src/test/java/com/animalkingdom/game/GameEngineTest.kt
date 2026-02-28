package com.animalkingdom.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class GameEngineTest {

    @Test
    fun initBoard_createsExpectedPairs() {
        val engine = GameEngine(Random(1))

        val board = engine.initBoard(Difficulty.MEDIUM_3X4)

        assertEquals(12, board.cards.size)
        val grouped = board.cards.groupBy { it.id }
        assertTrue(grouped.values.all { it.size == 2 })
    }

    @Test
    fun tapCard_marksMatchingCards() {
        val engine = GameEngine()
        val board = BoardState(
            difficulty = Difficulty.EASY_2X2,
            cards = listOf(
                CardState(id = 1, emoji = "🐶"),
                CardState(id = 1, emoji = "🐶"),
                CardState(id = 2, emoji = "🐱"),
                CardState(id = 2, emoji = "🐱")
            )
        )

        val firstTap = engine.tapCard(board, 0)
        val secondTap = engine.tapCard(firstTap, 1)

        assertTrue(secondTap.cards[0].isMatched)
        assertTrue(secondTap.cards[1].isMatched)
        assertEquals(1, secondTap.matchesFound)
    }

    @Test
    fun hideUnmatchedFaceUp_flipsBackCards() {
        val engine = GameEngine()
        val board = BoardState(
            difficulty = Difficulty.EASY_2X2,
            cards = listOf(
                CardState(id = 1, emoji = "🐶", isFaceUp = true),
                CardState(id = 2, emoji = "🐱", isFaceUp = true),
                CardState(id = 1, emoji = "🐶"),
                CardState(id = 2, emoji = "🐱")
            )
        )

        val hidden = engine.hideUnmatchedFaceUp(board)

        assertFalse(hidden.cards[0].isFaceUp)
        assertFalse(hidden.cards[1].isFaceUp)
    }
}
