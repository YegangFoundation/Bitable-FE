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
import com.example.bitable_fe.core.network.response.OrderResponse
import com.example.bitable_fe.core.ui.state.CoinUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinViewModel
import com.example.bitable_fe.feature.trade.screen.component.PercentSelector
import com.example.bitable_fe.feature.trade.screen.component.TradeInputRow
import com.example.bitable_fe.feature.trade.screen.component.TradeNumberPad
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton
import com.example.bitable_fe.core.ui.state.CoinDetailState
import com.example.bitable_fe.core.ui.state.OrderUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinDetailViewModel
import com.example.bitable_fe.core.ui.viewmodel.OrderViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel


@Composable
fun BuyScreen(
    symbol: String,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    userPreferencesViewModel: UserPreferencesViewModel = hiltViewModel()
) {
    val uiState by coinDetailViewModel.tickerState.collectAsState()
    val orderState by orderViewModel.state.collectAsState()

    // 유저 입력값
    var amount by remember { mutableStateOf("") }   // 수량
    var price by remember { mutableDoubleStateOf(0.0) }   // 현재가
    var total by remember { mutableDoubleStateOf(0.0) }   // 총액

    val accountId by userPreferencesViewModel.userIdFlow.collectAsState(initial = -1L)

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

    LaunchedEffect(orderState) {
        if (orderState is OrderUiState.Success) {
            val res = (orderState as OrderUiState.Success).data as OrderResponse
            println("✅ 매수 성공! 주문번호 = ${res.orderId}")
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

            // 🔹 타이틀 (고정)
            Text(
                text = symbol.uppercase(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )

            // 🔹 가격 입력 박스 (적당한 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.3f)              // ⭐ 중요: 비율 분배
                    .background(Color(0xFFF6F7F9), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TradeInputRow(label="수량", value=amount.ifBlank{"0"}, unit=symbol, bold=true)
                    TradeInputRow(label="가격", value=String.format("%,.0f", price), unit="KRW")
                    TradeInputRow(label="총액", value=String.format("%,.0f", total), unit="KRW", bold=true)
                }
            }

            Spacer(Modifier.height(8.dp))

            // 🔹 퍼센트 선택 (작은 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)              // ⭐ 조정 가능
            ) {
                PercentSelector { percent ->
                    val pct = percent.replace("%", "").toInt()
                    val ratio = pct / 100.0
                    amount = ratio.toString()
                    total = price * ratio
                }
            }

            Spacer(Modifier.height(8.dp))

            // 🔹 숫자 키패드 (가장 큰 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.2f)              // ⭐ 키패드 영역 크게
            ) {
                TradeNumberPad { key ->
                    when (key) {
                        "←" -> amount = amount.dropLast(1)
                        "00" -> if (amount.isNotEmpty()) amount += "00"
                        else -> amount += key
                    }
                    total = (amount.toDoubleOrNull() ?: 0.0) * price
                }
            }

            Spacer(Modifier.height(12.dp))

            // 🔹 매수 버튼 (고정 높이)
            Button(
                onClick = {
                    val amountKrw = total
                    if (amountKrw > 0) {
                        orderViewModel.buy(
                            accountId = accountId,
                            symbol = symbol,
                            amountKrw = amountKrw
                        )
                    }
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

            Spacer(Modifier.height(12.dp))
        }
    }


}
