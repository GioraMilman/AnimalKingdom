package com.animalkingdom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animalkingdom.audio.SoundEffects
import com.animalkingdom.game.BoardState
import com.animalkingdom.game.Difficulty
import com.animalkingdom.game.GameEngine
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val engine: GameEngine,
    private val settingsViewModel: SettingsViewModel,
    private val soundEffects: SoundEffects? = null
) : ViewModel() {

    private val _board = MutableStateFlow(engine.initBoard(Difficulty.EASY_2X2))
    val board: StateFlow<BoardState> = _board.asStateFlow()

    private var pendingHideJob: Job? = null

    fun startGame(difficulty: Difficulty) {
        pendingHideJob?.cancel()
        _board.value = engine.initBoard(difficulty)
    }

    fun tapCard(index: Int) {
        val before = _board.value
        val after = engine.tapCard(before, index)
        if (after == before) return
        _board.value = after

        val faceUpUnmatched = after.cards.count { it.isFaceUp && !it.isMatched }
        if (faceUpUnmatched == 2) {
            val ids = after.cards.filter { it.isFaceUp && !it.isMatched }.map { it.id }
            if (ids.distinct().size == 1) {
                soundEffects?.play(SoundEffects.Effect.MATCH, settingsViewModel.settings.value.soundEnabled)
                if (after.completed) {
                    soundEffects?.play(SoundEffects.Effect.WIN, settingsViewModel.settings.value.soundEnabled)
                    settingsViewModel.registerWin()
                    unlockNextDifficulty(after.difficulty)
                }
            } else {
                soundEffects?.play(SoundEffects.Effect.ERROR, settingsViewModel.settings.value.soundEnabled)
                pendingHideJob?.cancel()
                pendingHideJob = viewModelScope.launch {
                    delay(600)
                    _board.value = engine.hideUnmatchedFaceUp(_board.value)
                }
            }
        }
    }

    private fun unlockNextDifficulty(current: Difficulty) {
        val next = Difficulty.entries.getOrNull(current.ordinal + 1) ?: current
        settingsViewModel.unlockDifficulty(next)
    }
}
