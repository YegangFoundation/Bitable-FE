package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.feature.trade.screen.component.PercentSelector
import com.example.bitable_fe.feature.trade.screen.component.TradeInputRow
import com.example.bitable_fe.feature.trade.screen.component.TradeNumberPad
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun SellScreen(
    coinName: String = "엑스알피(리플)"
) {

    Scaffold(
        floatingActionButton = { VoiceFloatingButton() }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ◆ 상단 코인명
            Text(
                text = coinName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // ◆ 입력 박스 영역 (회색 박스)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF6F7F9), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    TradeInputRow(
                        label = "수량",
                        value = "2 BTC"
                    )

                    TradeInputRow(
                        label = "가격",
                        value = "126,556,000",
                        unit = "KRW"
                    )

                    TradeInputRow(
                        label = "총액",
                        value = "253,112,000",
                        unit = "KRW",
                        bold = true
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ◆ 퍼센트 선택
            PercentSelector(){}

            Spacer(Modifier.height(16.dp))

            // ◆ 숫자패드
            TradeNumberPad { }

            Spacer(Modifier.height(20.dp))

            // ◆ 매수 버튼
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD73D4A),
                    contentColor = Color.White
                )
            ) {
                Text("매수", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}