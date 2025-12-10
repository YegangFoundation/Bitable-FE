package com.example.bitable_fe.core.ui.component

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel
import java.io.File

@Composable
fun VoiceFloatingButton(
    viewModel: VoiceViewModel = hiltViewModel(),
    preferencesViewModel: UserPreferencesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    var recorder by remember { mutableStateOf<VoiceRecorder?>(null) }
    var audioFile by remember { mutableStateOf<File?>(null) }
    val userId by preferencesViewModel.userIdFlow.collectAsState(initial = -1L)

    FloatingActionButton(
        onClick = {
            if (userId == -1L) return@FloatingActionButton
            val permission = Manifest.permission.RECORD_AUDIO

            // 1️⃣ 권한 체크
            val granted = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            // 2️⃣ 권한 없으면 요청
            if (!granted) {
                (context as? Activity)?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(permission),
                        1000
                    )
                }
                return@FloatingActionButton
            }

            // 3️⃣ 권한 있을 때만 녹음 기능 동작
            if (!isRecording) {
                // 🔥 매번 새 파일 생성
                val file = File(context.cacheDir, "voice_${System.currentTimeMillis()}.mp4")
                audioFile = file

                // 🔥 녹음기 새로 생성
                recorder = VoiceRecorder(file).also {
                    it.start()
                }
                isRecording = true

            } else {
                // 🔥 녹음 종료
                recorder?.stop()
                isRecording = false

                // 🔥 서버로 업로드
                audioFile?.let {
                    viewModel.sendRecordedAudio(userId, it)
                }

                // cleanup
                recorder = null

                Toast.makeText(context, "주문 체결 완료", Toast.LENGTH_SHORT).show()
                viewModel.tts("주문 체결이 완료되었습니다.")
            }
        },
        containerColor = if (isRecording) Color.Red else Color(0xFF006AFF),
        shape = CircleShape,

    ) {
        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = "voice",
            tint = Color.White
        )
    }
}

