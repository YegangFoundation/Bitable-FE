package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.bitable_fe.core.ui.state.CoinDetailState
import com.example.bitable_fe.core.ui.viewmodel.CoinDetailViewModel


@Composable
fun BuyScreen(
    symbol: String,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
) {
    val uiState by coinDetailViewModel.tickerState.collectAsState()

    // 유저 입력값
    var amount by remember { mutableStateOf("") }   // 수량
    var price by remember { mutableStateOf(0.0) }   // 현재가
    var total by remember { mutableStateOf(0.0) }   // 총액

    // 🔥 티커 호출로 가격 데이터 가져오기
    LaunchedEffect(symbol) {
        coinDetailViewModel.loadTicker(symbol)
    }

    // 🔥 Ticker 응답 반영
    LaunchedEffect(uiState) {
        if (uiState is CoinDetailState.Success) {
            val ticker = (uiState as CoinDetailState.Success).data

            price = ticker.trade_price
            total = (amount.toDoubleOrNull() ?: 0.0) * price
        }
    }

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

            // 🔥 코인명 표시
            Text(
                text = symbol.uppercase(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // 🔥 가격 입력 박스
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

            // 퍼센트 선택기
            PercentSelector { percent ->
                val pct = percent.replace("%", "").toInt()
                val ratio = pct / 100.0
                amount = ratio.toString()
                total = price * ratio
            }

            Spacer(Modifier.height(16.dp))

            // 🔢 숫자 패드 입력
            TradeNumberPad { key ->
                when (key) {
                    "←" -> amount = amount.dropLast(1)
                    "00" -> if (amount.isNotEmpty()) amount += "00"
                    else -> amount += key
                }

                total = (amount.toDoubleOrNull() ?: 0.0) * price
            }

            Spacer(Modifier.height(20.dp))

            // 🔥 매수 버튼
            Button(
                onClick = {
                    // TODO: 매수 API 연동
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006AFF),
                    contentColor = Color.White
                )
            ) {
                Text("매수", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}
