package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bitable_fe.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onNext: () -> Unit){
    LaunchedEffect(Unit) {
        delay(1500L)
        onNext()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF3181F4))
            .semantics {
                contentDescription = "비트에이블 스플래시 화면, 잠시 후 자동으로 다음 화면으로 이동합니다"
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Image(
            painter = painterResource(R.drawable.onboarding),
            contentDescription = "비트에이블 로고 이미지",
            modifier = Modifier
                .height(233.dp)
                .width(346.dp)
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview(){
    SplashScreen(){}
}