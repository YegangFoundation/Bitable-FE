package com.example.bitable_fe.core.ui.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel
import java.io.File

@Composable
fun VoiceFloatingButton(
    viewModel: VoiceViewModel,
    userId: Long = 1L
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }

    val audioFile = remember {
        File(context.cacheDir, "voice_${System.currentTimeMillis()}.mp4")
    }
    val recorder = remember { VoiceRecorder(audioFile) }

    FloatingActionButton(
        onClick = {
            if (!isRecording) {
                recorder.start()
                isRecording = true
            } else {
                recorder.stop()
                isRecording = false
                viewModel.sendRecordedAudio(userId, audioFile)
            }
        },
        containerColor = if (isRecording) Color.Red else Color(0xFF006AFF),
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = "voice",
            tint = Color.White
        )
    }
}
