package com.animalkingdom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animalkingdom.game.Difficulty
import com.animalkingdom.settings.ParentSettings
import com.animalkingdom.settings.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore,
    initialSettings: ParentSettings = ParentSettings(
        soundEnabled = true,
        maxUnlockedDifficulty = Difficulty.EASY_2X2,
        totalWins = 0
    )
) : ViewModel() {

    val settings: StateFlow<ParentSettings> = settingsDataStore.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initialSettings)

    fun toggleSound(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setSoundEnabled(enabled)
        }
    }

    fun unlockDifficulty(difficulty: Difficulty) {
        viewModelScope.launch {
            settingsDataStore.unlockDifficulty(difficulty)
        }
    }

    fun registerWin() {
        viewModelScope.launch {
            settingsDataStore.incrementWins()
        }
    }
}
