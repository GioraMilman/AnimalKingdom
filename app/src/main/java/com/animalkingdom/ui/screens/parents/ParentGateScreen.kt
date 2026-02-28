package com.animalkingdom.ui.screens.parents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.animalkingdom.game.Difficulty
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ParentGateScreen(
    soundEnabled: Boolean,
    unlockedDifficulty: Difficulty,
    onSoundChange: (Boolean) -> Unit,
    onUnlockDifficulty: (Difficulty) -> Unit,
    onBack: () -> Unit
) {
    var gateOpen by remember { mutableStateOf(false) }
    var a by remember { mutableIntStateOf(Random.nextInt(2, 9)) }
    var b by remember { mutableIntStateOf(Random.nextInt(2, 9)) }
    var answer by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Parents Area", style = MaterialTheme.typography.headlineSmall)
        Text("Long-press below, then solve the math question.")

        Text(
            text = if (gateOpen) "Gate unlocked ✅" else "Hold here for 2 seconds to unlock",
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {},
                    onLongClick = { gateOpen = true }
                )
                .padding(16.dp)
        )

        if (gateOpen) {
            Text("What is $a + $b ?")
            OutlinedTextField(value = answer, onValueChange = { answer = it }, label = { Text("Answer") })
            Button(onClick = {
                val isCorrect = answer.toIntOrNull() == (a + b)
                if (isCorrect) {
                    feedback = "Access granted"
                } else {
                    feedback = "Try again"
                    a = Random.nextInt(2, 9)
                    b = Random.nextInt(2, 9)
                }
            }) { Text("Submit") }

            Text(feedback)

            if (feedback == "Access granted") {
                Text("Sound effects")
                Switch(checked = soundEnabled, onCheckedChange = onSoundChange)

                Difficulty.entries.forEach { difficulty ->
                    val enabled = difficulty.ordinal <= unlockedDifficulty.ordinal + 1
                    Button(
                        onClick = { onUnlockDifficulty(difficulty) },
                        enabled = enabled
                    ) {
                        Text("Unlock ${difficulty.rows}x${difficulty.columns}")
                    }
                }
            }
        }

        Button(onClick = onBack) { Text("Back") }
    }
}
