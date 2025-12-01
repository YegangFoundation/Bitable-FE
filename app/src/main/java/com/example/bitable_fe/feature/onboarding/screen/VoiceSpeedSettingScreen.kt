package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

@Composable
fun VoiceSpeedSettingScreen(modifier: Modifier = Modifier, currentSpeed: Float = 1.0f ,onNextClicked : () -> Unit){

    var speed by remember { mutableStateOf(currentSpeed) }

    Column(
        modifier = modifier
            .fillMaxSize(),
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
                .border(
                    width = 4.dp,
                    color = Color.Black
                ),
            contentAlignment = Alignment.Center
        ){
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
            value = speed,
            onValueChange = {speed = it},
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
            onClick = onNextClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3181F4)
            )
        ) {
            Text("다음", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview
@Composable
private fun VoiceSpeedSettingScreenPreview(){
    VoiceSpeedSettingScreen() { }
}