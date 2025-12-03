package com.example.bitable_fe.feature.setting.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.component.BottomBar
import com.example.bitable_fe.core.ui.viewmodel.SettingsViewModel

@Composable
fun InfoLevelSettingScreen(
    vm: SettingsViewModel = hiltViewModel()
) {
    val infoLevel by vm.infoLevel.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Text("음성 정보 단계", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        listOf(
            "요약형" to 1,
            "상세형" to 2
        ).forEach { (label, level) ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { vm.setInfoLevel(level) }
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label, fontSize = 20.sp)
                RadioButton(
                    selected = (infoLevel == level),
                    onClick = { vm.setInfoLevel(level) }
                )
            }
        }
    }
}

