package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.data.model.VoiceOption
import com.example.bitable_fe.feature.onboarding.screen.component.InfoCard
import com.example.bitable_fe.core.ui.theme.CustomTypography
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserViewModel
import com.example.bitable_fe.core.network.request.UpdateSettingsRequest
import com.example.bitable_fe.core.ui.state.UserUiState

@Composable
fun VoiceInformationAmountSettingScreen(
    initialOption: VoiceOption = VoiceOption.DETAIL,
    userViewModel: UserViewModel = hiltViewModel(),
    preferencesViewModel: UserPreferencesViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    val backgroundColor = Color(0xFFF7F8FA)

    // ⭐ DataStore에서 userId 가져오기
    val userId by preferencesViewModel.userIdFlow.collectAsState(initial = null)

    if (userId == null) {
        // DataStore 로딩 중
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("유저 정보를 불러오는 중...")
        }
        return
    }

    // ⭐ ViewModel UI 상태
    val uiState by userViewModel.state.collectAsState()

    // updateSettings 성공 시 다음 화면
    LaunchedEffect(uiState) {
        if (uiState is UserUiState.Success) {
            onClick()
        }
    }

    // ⭐ 현재 선택된 옵션 state
    var selectedOption by remember(initialOption) { mutableStateOf(initialOption) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = backgroundColor
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.weight(0.5f))

            Text(
                text = "음성 정보 단계",
                style = CustomTypography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold),
                color = Color.Black
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "각 화면에서 읽어주는 정보의 양",
                style = CustomTypography.bodyMedium,
                color = Color.Gray,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(40.dp))

            // ⭐ 정보량 선택 카드
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = "상세형",
                    content = "“현재 비트코인의 가격은 1억 2,500만원 원으로, 지금 시장에서 형성된 실시간 시세입니다.”",
                    isSelected = selectedOption == VoiceOption.DETAIL,
                    onClick = { selectedOption = VoiceOption.DETAIL }
                )

                Spacer(Modifier.height(16.dp))

                InfoCard(
                    title = "요약형",
                    content = "“비트코인은 지금 1억 2,500만원 이에요.”",
                    isSelected = selectedOption == VoiceOption.SUMMARY,
                    onClick = { selectedOption = VoiceOption.SUMMARY }
                )
            }

            Spacer(Modifier.weight(1f))

            // ⭐ updateSettings 적용 버튼
            Button(
                onClick = {
                    val infoLevel = when (selectedOption) {
                        VoiceOption.SUMMARY -> 1
                        VoiceOption.DETAIL -> 2
                    }

                    userViewModel.updateSettings(
                        userId = userId!!,
                        req = UpdateSettingsRequest(
                            infoLevel = infoLevel
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "다음",
                    fontSize = 24.sp,
                    style = CustomTypography.titleMedium.copy(color = Color.White)
                )
            }
        }
    }
}
