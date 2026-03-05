package com.animalkingdom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animalkingdom.audio.SoundEffects
import com.animalkingdom.game.Difficulty
import com.animalkingdom.game.GameEngine
import com.animalkingdom.settings.SettingsDataStore
import com.animalkingdom.ui.screens.game.GameScreen
import com.animalkingdom.ui.screens.menu.MenuScreen
import com.animalkingdom.ui.screens.parents.ParentGateScreen
import com.animalkingdom.ui.screens.rewards.StickerBookScreen
import com.animalkingdom.ui.theme.AnimalMatchTheme
import com.animalkingdom.viewmodel.GameViewModel
import com.animalkingdom.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimalMatchTheme {
                AnimalMatchApp()
            }
        }
    }
}

@Composable
private fun AnimalMatchApp() {
    val navController = rememberNavController()
    val settingsViewModel = remember { SettingsViewModel(SettingsDataStore(navController.context)) }
    val gameViewModel = remember {
        GameViewModel(
            engine = GameEngine(),
            settingsViewModel = settingsViewModel,
            soundEffects = SoundEffects(navController.context)
        )
    }
    val settings by settingsViewModel.settings.collectAsState()

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(
                unlockedDifficulty = settings.maxUnlockedDifficulty,
                onStart = { difficulty: Difficulty ->
                    gameViewModel.startGame(difficulty)
                    navController.navigate("game")
                },
                onRewards = { navController.navigate("rewards") },
                onParents = { navController.navigate("parent_gate") }
            )
        }
        composable("game") {
            val board by gameViewModel.board.collectAsState()
            GameScreen(
                board = board,
                onCardTap = gameViewModel::tapCard,
                onPeek = gameViewModel::usePeek,
                onBackToMenu = { navController.popBackStack("menu", false) }
            )
        }
        composable("rewards") {
            StickerBookScreen(
                totalWins = settings.totalWins,
                onBack = { navController.popBackStack() }
            )
        }
        composable("parent_gate") {
            ParentGateScreen(
                soundEnabled = settings.soundEnabled,
                unlockedDifficulty = settings.maxUnlockedDifficulty,
                onSoundChange = settingsViewModel::toggleSound,
                onUnlockDifficulty = settingsViewModel::unlockDifficulty,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
