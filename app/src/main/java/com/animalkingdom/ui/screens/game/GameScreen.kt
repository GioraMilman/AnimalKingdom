package com.animalkingdom.ui.screens.game

import androidx.compose.animation.core.spring
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.animalkingdom.game.BoardState
import com.animalkingdom.game.CardState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.AnimatedContent

@Composable
fun GameScreen(
    board: BoardState,
    onCardTap: (Int) -> Unit,
    onBackToMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Moves: ${board.moves}", fontWeight = FontWeight.Bold)
        Text("Matches: ${board.matchesFound}/${board.difficulty.pairCount}")

        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(board.difficulty.columns),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(board.cards) { index, card ->
                CardTile(card = card, onClick = { onCardTap(index) })
            }
        }

        if (board.completed) {
            Text(
                "Great job! You matched all animals!",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Button(onClick = onBackToMenu, modifier = Modifier.fillMaxWidth()) {
            Text("Back to menu")
        }
    }
}

@Composable
private fun CardTile(card: CardState, onClick: () -> Unit) {
    val shown = card.isFaceUp || card.isMatched

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(enabled = !card.isMatched, onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (card.isMatched) Color(0xFFC8E6C9)
                    else Color(0xFFFFF3E0)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = shown,
                label = "flip",
                transitionSpec = {
                    fadeIn(animationSpec = spring()) togetherWith
                            fadeOut(animationSpec = spring())
                }
            ) { isShown ->
                Text(
                    text = if (isShown) card.emoji else "❓",
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }
}
