package com.example.bitable_fe.feature.setting.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.R
import com.example.bitable_fe.core.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsMainScreen(
    vm: SettingsViewModel = hiltViewModel(),
    onGoSpeed: () -> Unit,
    onGoInfoLevel: () -> Unit,
    onGoCustomize: () -> Unit,
    onGoCommandExample: () -> Unit,
    onResetClick: () -> Unit,
) {

    val users = vm.users.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 13.dp)
        ) {
            Text("설정", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "null",
                )
                Column() {
                    Text(
                        users.value?.name ?: "User",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text("Email@gmail.com", fontSize = 20.sp, color = Color(0xFF6B7583))
                }
            }

        }
        Spacer(Modifier.height(24.dp))

        SettingItem("음성 속도 설정") { onGoSpeed() }
        SettingItem("음성 정보 단계") { onGoInfoLevel() }
        SettingItem("읽기 항목 커스터마이즈") { onGoCustomize() }
        SettingItem("음성 명령 예시") { onGoCommandExample() }

        Spacer(Modifier
            .height(16.dp)
            .background(Color.Gray))

        Text(
            "개인정보 및 데이터 리셋",
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onResetClick() }
        )
    }

}

@Composable
private fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .clickable(onClick = onClick)
            .border(1.dp, Color.Gray),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
