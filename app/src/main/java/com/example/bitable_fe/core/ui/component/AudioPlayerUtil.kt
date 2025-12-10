package com.example.bitable_fe.core.ui.component

import android.media.MediaPlayer
import java.io.File

object AudioPlayerUtil {
    private var player: MediaPlayer? = null

    fun playByteArray(data: ByteArray, onComplete: () -> Unit = {}) {
        stop()

        val temp = File.createTempFile("tts_audio", ".mp3")
        temp.writeBytes(data)

        player = MediaPlayer().apply {
            setDataSource(temp.absolutePath)
            setOnPreparedListener { start() }
            setOnCompletionListener {
                onComplete()
                stop()
            }
            prepareAsync()   // 🔥 중요
        }
    }

    fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}
