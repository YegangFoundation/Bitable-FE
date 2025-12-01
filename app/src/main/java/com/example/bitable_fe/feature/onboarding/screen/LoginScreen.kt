package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier, loginClicked: () -> Unit){

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .width(260.dp)
        )
        Spacer(Modifier.height(48.dp))
        OutlinedTextField(
            state = rememberTextFieldState(),
            lineLimits = TextFieldLineLimits.SingleLine,
            placeholder = { Text("전화번호 입력") },
            modifier = Modifier
                .width(345.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        OutlinedTextField(
            state = rememberTextFieldState(),
            lineLimits = TextFieldLineLimits.SingleLine,
            placeholder = { Text("이름 입력") },
            modifier = Modifier
                .width(345.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(Modifier.height(48.dp))
        Button(
            onClick = loginClicked,
            modifier = Modifier
                .width(345.dp)
                .height(64.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3181F4)
            )
        ) {
            Text(
                text = "시작",
                fontSize = 24.sp
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview(){
    LoginScreen(){}
}