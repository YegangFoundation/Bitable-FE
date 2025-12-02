package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.network.request.UpdateSettingsRequest
import com.example.bitable_fe.core.ui.state.UserUiState
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserViewModel

@Composable
fun VoiceSpeedSettingScreen(
    modifier: Modifier = Modifier,
    currentSpeed: Double = 1.0,
    userViewModel: UserViewModel = hiltViewModel(),
    preferencesViewModel: UserPreferencesViewModel = hiltViewModel(),
    onNextClicked: () -> Unit
) {
    val userId by preferencesViewModel.userIdFlow.collectAsState(initial = null)
    var speed by remember { mutableDoubleStateOf(currentSpeed) }

    val uiState by userViewModel.state.collectAsState()

    // 👉 updateSettings 성공 시 다음 화면 이동
    LaunchedEffect(uiState) {
        onNextClicked() // TODO 수정되면 삭제
        /* // TODO 수정 되면 주석 제거
        if (uiState is UserUiState.Success) {
            onNextClicked()
        }

         */
    }

    // 👉 userId 로드 중이면 UI 렌더링 안 함
    if (userId == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("정보 로드 중...", color = Color.Gray)
        }
        return
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.5f))

        Text(
            text = "음성 속도 설정",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "정보를 읽어주는 TTS 음성 속도",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .width(340.dp)
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(width = 4.dp, color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format("%.1fx", speed),
                    fontSize = 64.sp
                )
                Text(
                    text = "현재 설정된 음성 속도",
                    fontSize = 24.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Slider(
            value = speed.toFloat(),
            onValueChange = { speed = it.toDouble() },
            valueRange = 0.5f..2.0f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF6B7583)
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("느리게", fontSize = 24.sp, color = Color.Gray)
            Text("빠르게", fontSize = 24.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                userViewModel.updateSettings(
                    userId = userId!!,
                    req = UpdateSettingsRequest(ttsSpeed = speed)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3181F4)
            )
        ) {
            Text(
                "다음",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun VoiceSpeedSettingScreenPreview() {
    VoiceSpeedSettingScreen(onNextClicked = {})
}
