package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.data.model.VoiceOption
import com.example.bitable_fe.feature.onboarding.screen.component.InfoCard
import com.example.bitable_fe.ui.theme.CustomTypography

@Composable
fun VoiceInformationAmountSettingScreen(onClick: () -> Unit) {
    val backgroundColor = Color(0xFFF7F8FA)

    // **선택 상태 (Bool state) 저장**
    // 선택된 옵션을 저장하는 mutable state를 선언합니다. 초기값은 상세형으로 설정했습니다.
    var selectedOption by remember { mutableStateOf(VoiceOption.DETAIL) } // Boolean 대신 Enum을 사용하여 어떤 카드가 선택되었는지 명확하게 표현합니다.

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
            // 상단 제목 섹션 (이전과 동일)
            Spacer(modifier = Modifier.weight(0.5f))

            Text(
                text = "음성 정보 단계",
                style = CustomTypography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "각 화면에서 읽어주는 정보의 양",
                style = CustomTypography.bodyMedium,
                color = Color.Gray,
                fontSize = 20.sp
            )

            // 카드 그룹 중앙 정렬을 위한 Spacer
            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 상세형 카드
                InfoCard(
                    title = "상세형",
                    content = "“현재 비트코인의 가격은 1억 2,500만원 원으로, 지금 시장에서 형성된 실시간 시세입니다.”",
                    // 현재 카드가 선택되었는지 확인하는 Boolean 값 전달
                    isSelected = selectedOption == VoiceOption.DETAIL,
                    // 클릭 시 상태를 업데이트하는 함수 전달
                    onClick = { selectedOption = VoiceOption.DETAIL }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 요약형 카드
                InfoCard(
                    title = "요약형",
                    content = "“비트코인은 지금 1억 2,500만원 이에요.”",
                    // 현재 카드가 선택되었는지 확인하는 Boolean 값 전달
                    isSelected = selectedOption == VoiceOption.SUMMARY,
                    // 클릭 시 상태를 업데이트하는 함수 전달
                    onClick = { selectedOption = VoiceOption.SUMMARY }
                )
            }

            // 하단 버튼을 위한 Spacer
            Spacer(modifier = Modifier.weight(1f))

            // 하단 버튼
            Button(
                // 버튼 활성화 여부는 선택 상태에 따라 결정할 수 있지만, 여기서는 항상 활성화 상태로 둡니다.
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("다음", fontSize = 24.sp, style = CustomTypography.titleMedium.copy(color = Color.White))
            }
        }
    }
}


@Preview
@Composable
private fun VoiceInformationAmountSettingScreenPreview(){
    VoiceInformationAmountSettingScreen{}
}