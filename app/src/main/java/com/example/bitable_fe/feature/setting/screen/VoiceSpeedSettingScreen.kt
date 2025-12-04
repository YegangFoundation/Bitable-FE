package com.example.bitable_fe.feature.setting.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.SettingsViewModel

@Composable
fun SpeedSettingScreen(
    vm: SettingsViewModel = hiltViewModel()
) {
    val selectedSpeed by vm.ttsSpeed.collectAsState()


    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 13.dp)
        ) {
            Text("음성 속도 설정", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        }
        Spacer(Modifier.height(24.dp))

        listOf(1.0, 1.5, 2.0, 2.5, 3.0).forEach { speed ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { vm.setSpeed(speed) }
                    .padding(vertical = 20.dp)
                    .border(1.dp, Color.Gray),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RadioButton(
                    selected = selectedSpeed == speed,
                    onClick = { vm.setSpeed(speed) },
                    modifier = Modifier.size(20.dp)
                )

                Text("${speed}x", fontSize = 20.sp)
            }

        }
    }
}

