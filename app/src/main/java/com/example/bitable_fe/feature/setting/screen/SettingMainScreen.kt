package com.example.bitable_fe.feature.setting.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.component.BottomBar
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

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Text("설정", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        SettingItem("음성 속도 설정") { onGoSpeed() }
        SettingItem("음성 정보 단계") { onGoInfoLevel() }
        SettingItem("읽기 항목 커스터마이즈") { onGoCustomize() }
        SettingItem("음성 명령 예시") { onGoCommandExample() }

        Spacer(Modifier.height(32.dp))

        Text(
            "개인정보 및 데이터 리셋",
            color = Color.Red,
            modifier = Modifier.clickable { onResetClick() }
        )
    }

}

@Composable
private fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = 18.sp)
        Text(">", fontSize = 22.sp, color = Color.Gray)
    }
}
