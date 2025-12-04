package com.example.bitable_fe.core.ui.component

import android.media.MediaRecorder
import java.io.File

class VoiceRecorder(
    private val output: File
) {
    private var recorder: MediaRecorder? = null

    fun start() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(output.absolutePath)
            prepare()
            start()
        }
    }

    fun stop() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }
}
