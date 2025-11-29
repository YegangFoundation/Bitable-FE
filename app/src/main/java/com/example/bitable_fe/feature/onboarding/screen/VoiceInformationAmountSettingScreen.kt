package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VoiceInformationAmountSettingScreen(selectType: String = "detail", onSelect: (String) -> Unit, onNext: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "음성 정보 단계",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "각 화면에서 보여주는 정보의 양",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}