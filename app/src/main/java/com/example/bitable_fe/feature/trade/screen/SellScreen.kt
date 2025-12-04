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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.network.response.MarketData
import com.example.bitable_fe.core.ui.state.CoinUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinViewModel
import com.example.bitable_fe.feature.trade.screen.component.PercentSelector
import com.example.bitable_fe.feature.trade.screen.component.TradeInputRow
import com.example.bitable_fe.feature.trade.screen.component.TradeNumberPad
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel

@Composable
fun SellScreen(
    symbol: String,      // 예: "XRP"
    coinViewModel: CoinViewModel = hiltViewModel(),
    voiceViewModel: VoiceViewModel = hiltViewModel(),
    onSellConfirm: () -> Unit = {},
) {
    val uiState by coinViewModel.state.collectAsState()

    // 입력값
    var amount by remember { mutableStateOf("") }   // 수량
    var price by remember { mutableStateOf(0.0) }   // 현재가
    var total by remember { mutableStateOf(0.0) }   // 총액

    // 코인정보 로드
    LaunchedEffect(symbol) {
        coinViewModel.getCoin(symbol)
    }

    // API 응답 반영
    LaunchedEffect(uiState) {
        if (uiState is CoinUiState.Success) {
            val data = (uiState as CoinUiState.Success).data
            if (data is MarketData) {
                price = data.trade_price
                total = (amount.toDoubleOrNull() ?: 0.0) * price
            }
        }
    }

    Scaffold(
        floatingActionButton = { VoiceFloatingButton(voiceViewModel) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 제목 - 코인명
            Text(
                text = symbol,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // 박스 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF6F7F9), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    // 수량
                    TradeInputRow(
                        label = "수량",
                        value = amount.ifBlank { "0" },
                        unit = symbol,
                        bold = true
                    )

                    // 현재가
                    TradeInputRow(
                        label = "가격",
                        value = String.format("%,.0f", price),
                        unit = "KRW"
                    )

                    // 총액
                    TradeInputRow(
                        label = "총액",
                        value = String.format("%,.0f", total),
                        unit = "KRW",
                        bold = true
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // 퍼센트 선택
            PercentSelector { percent ->
                val pct = percent.replace("%", "").toInt()
                val calc = pct / 100.0
                amount = calc.toString()
                total = price * calc
            }

            Spacer(Modifier.height(16.dp))

            // 숫자패드
            TradeNumberPad { key ->
                when (key) {
                    "←" -> {
                        amount = amount.dropLast(1)
                    }
                    "00" -> {
                        // 기존 값이 비어있으면 00 입력 X
                        if (amount.isNotEmpty()) amount += "00"
                    }
                    else -> { // 숫자
                        amount += key
                    }
                }

                total = (amount.toDoubleOrNull() ?: 0.0) * price
            }

            Spacer(Modifier.height(20.dp))

            // 매도 버튼
            Button(
                onClick = { onSellConfirm() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD73D4A),
                    contentColor = Color.White
                )
            ) {
                Text("매도", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}
