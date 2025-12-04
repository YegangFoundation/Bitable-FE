package com.example.bitable_fe.feature.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.SettingsViewModel

@Composable
fun InfoLevelSettingScreen(
    vm: SettingsViewModel = hiltViewModel()
) {
    val infoLevel by vm.infoLevel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        /** ------------------------------
         *  상단 타이틀
         * ------------------------------ */
        Text(
            text = "음성 정보 단계",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        /** ------------------------------
         *  옵션 리스트 박스
         * ------------------------------ */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, RoundedCornerShape(16.dp))
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            val options = listOf(
                "요약형" to 1,
                "상세형" to 2
            )

            options.forEachIndexed { index, (label, level) ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { vm.setInfoLevel(level) }
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (infoLevel == level),
                        onClick = { vm.setInfoLevel(level) }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = label,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                if (index != options.lastIndex) {
                    Divider(color = Color(0xFFE5E5E5))
                }
            }
        }
    }
}
