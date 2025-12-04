package com.example.bitable_fe.core.ui.component

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel
import java.io.File

@Composable
fun VoiceFloatingButton(
    viewModel: VoiceViewModel = hiltViewModel(),
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
            val permission = Manifest.permission.RECORD_AUDIO

            // 1️⃣ 권한 체크
            val granted = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                // 2️⃣ 권한 요청
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(permission),
                    1000
                )
                return@FloatingActionButton  // 권한 없으면 녹음 시작 X
            }

            // 3️⃣ 권한이 있을 때만 녹음 기능 실행
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

