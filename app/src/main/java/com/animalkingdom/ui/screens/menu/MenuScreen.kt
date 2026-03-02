package com.animalkingdom.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.animalkingdom.game.Difficulty

@Composable
fun MenuScreen(
    unlockedDifficulty: Difficulty,
    onStart: (Difficulty) -> Unit,
    onRewards: () -> Unit,
    onParents: () -> Unit
) {
    val context = LocalContext.current
    val introResourceId = remember {
        context.resources.getIdentifier("introImage", "drawable", context.packageName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically)
    ) {
        if (introResourceId != 0) {
            Image(
                painter = painterResource(id = introResourceId),
                contentDescription = "All animals",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
        }

        Text("Animal Match", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Choose difficulty")

        Difficulty.entries.forEach { difficulty ->
            val enabled = difficulty.ordinal <= unlockedDifficulty.ordinal
            Button(onClick = { onStart(difficulty) }, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
                Text("${difficulty.rows}x${difficulty.columns}")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onRewards) { Text("Sticker Book") }
            Button(onClick = onParents) { Text("Parents ⚙️") }
        }
    }
}
