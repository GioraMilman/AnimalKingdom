package com.animalkingdom.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundEffects(private val context: Context) {

    enum class Effect { MATCH, WIN, TAP, STICKER_UNLOCK, ERROR }

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(4)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private val soundIds = mutableMapOf<Effect, Int>()

    fun register(effect: Effect, rawResId: Int) {
        soundIds[effect] = soundPool.load(context, rawResId, 1)
    }

    fun play(effect: Effect, enabled: Boolean) {
        if (!enabled) return
        val id = soundIds[effect] ?: return
        soundPool.play(id, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}