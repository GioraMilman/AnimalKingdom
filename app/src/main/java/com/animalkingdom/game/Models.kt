package com.animalkingdom.game

enum class Difficulty(val rows: Int, val columns: Int, val pairCount: Int) {
    EASY_2X2(rows = 2, columns = 2, pairCount = 2),
    MEDIUM_3X4(rows = 3, columns = 4, pairCount = 6),
    HARD_4X4(rows = 4, columns = 4, pairCount = 8)
}

data class CardState(
    val id: Int,
    val animalName: String,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)

data class BoardState(
    val difficulty: Difficulty,
    val cards: List<CardState>,
    val moves: Int = 0,
    val matchesFound: Int = 0,
    val completed: Boolean = false,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val peekCharges: Int = 2
)

data class Sticker(
    val id: String,
    val title: String,
    val unlocked: Boolean
)
