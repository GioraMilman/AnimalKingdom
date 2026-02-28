package com.animalkingdom.ui.screens.rewards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StickerBookScreen(totalWins: Int, onBack: () -> Unit) {
    val stickers = listOf(
        "Puppy Pal" to (totalWins >= 1),
        "Jungle Star" to (totalWins >= 3),
        "Safari Champ" to (totalWins >= 5),
        "Ocean Hero" to (totalWins >= 8)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Sticker Book")
        Text("Wins: $totalWins")

        stickers.forEach { (name, unlocked) ->
            Card {
                Row(modifier = Modifier.padding(16.dp)) {
                    Text(if (unlocked) "⭐ $name" else "🔒 Locked Sticker")
                }
            }
        }

        Button(onClick = onBack) { Text("Back") }
    }
}
