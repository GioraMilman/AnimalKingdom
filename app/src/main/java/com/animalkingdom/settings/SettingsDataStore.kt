package com.animalkingdom.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.animalkingdom.game.Difficulty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "animal_match_settings")

class SettingsDataStore(private val context: Context) {

    val settings: Flow<ParentSettings> = context.dataStore.data.map { pref ->
        ParentSettings(
            soundEnabled = pref[SOUND_ENABLED] ?: true,
            maxUnlockedDifficulty = Difficulty.entries[(pref[MAX_UNLOCKED_DIFFICULTY_INDEX] ?: 0)
                .coerceIn(0, Difficulty.entries.lastIndex)],
            totalWins = pref[TOTAL_WINS] ?: 0
        )
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SOUND_ENABLED] = enabled
        }
    }

    suspend fun unlockDifficulty(difficulty: Difficulty) {
        context.dataStore.edit { prefs ->
            val current = prefs[MAX_UNLOCKED_DIFFICULTY_INDEX] ?: 0
            prefs[MAX_UNLOCKED_DIFFICULTY_INDEX] = maxOf(current, difficulty.ordinal)
        }
    }

    suspend fun incrementWins() {
        context.dataStore.edit { prefs ->
            val current = prefs[TOTAL_WINS] ?: 0
            prefs[TOTAL_WINS] = current + 1
        }
    }

    companion object {
        private val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        private val MAX_UNLOCKED_DIFFICULTY_INDEX = intPreferencesKey("max_unlocked_difficulty_index")
        private val TOTAL_WINS = intPreferencesKey("total_wins")
    }
}

data class ParentSettings(
    val soundEnabled: Boolean,
    val maxUnlockedDifficulty: Difficulty,
    val totalWins: Int
)
