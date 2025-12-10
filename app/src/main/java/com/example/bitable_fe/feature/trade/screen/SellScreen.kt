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
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.example.bitable_fe.core.network.response.OrderResponse
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton
import com.example.bitable_fe.core.ui.state.CoinDetailState
import com.example.bitable_fe.core.ui.state.OrderUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinDetailViewModel
import com.example.bitable_fe.core.ui.viewmodel.OrderViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel
import com.example.bitable_fe.feature.trade.screen.component.PercentSelector
import com.example.bitable_fe.feature.trade.screen.component.TradeInputRow
import com.example.bitable_fe.feature.trade.screen.component.TradeNumberPad
@Composable
fun SellScreen(
    symbol: String,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    userPref: UserPreferencesViewModel = hiltViewModel(),
    voiceViewModel: VoiceViewModel = hiltViewModel(),
    onSellConfirm: () -> Unit = {}
) {
    val uiState by coinDetailViewModel.tickerState.collectAsState()
    val orderState by orderViewModel.state.collectAsState()

    var amount by remember { mutableStateOf("") }
    var price by remember { mutableDoubleStateOf(0.0) }
    var total by remember { mutableStateOf(0.0) }

    val accountId by userPref.userIdFlow.collectAsState(initial = -1L)

    LaunchedEffect(symbol) {
        coinDetailViewModel.loadTicker(symbol)
    }

    LaunchedEffect(uiState) {
        if (uiState is CoinDetailState.Success) {
            val ticker = (uiState as CoinDetailState.Success).data
            price = ticker.trade_price
            total = (amount.toDoubleOrNull() ?: 0.0) * price
        }
    }

    LaunchedEffect(orderState) {
        when (orderState) {
            is OrderUiState.Success -> {
                val res = (orderState as OrderUiState.Success).data as OrderResponse
                println("✅ 매도 성공! 주문번호 = ${res.orderId}")
                onSellConfirm()
            }
            is OrderUiState.Error -> {
                println("❌ 매도 실패: ${(orderState as OrderUiState.Error).msg}")
            }
            else -> {}
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

            // 🔹 타이틀 (비율 없음)
            Text(
                text = symbol.uppercase(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // 🔹 가격 박스 (중간 크기 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.3f)
                    .background(Color(0xFFF6F7F9), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TradeInputRow(
                        label = "수량",
                        value = amount.ifBlank { "0" },
                        unit = symbol,
                        bold = true
                    )

                    TradeInputRow(
                        label = "가격",
                        value = String.format("%,.0f", price),
                        unit = "KRW"
                    )

                    TradeInputRow(
                        label = "총액",
                        value = String.format("%,.0f", total),
                        unit = "KRW",
                        bold = true
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // 🔹 퍼센트 선택 (작은 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                PercentSelector { percent ->
                    val pct = percent.replace("%", "").toInt()
                    val ratio = pct / 100.0
                    amount = ratio.toString()
                    total = price * ratio
                }
            }

            Spacer(Modifier.height(8.dp))

            // 🔹 키패드 (가장 큰 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.2f)
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

            // 🔥 매도 버튼 (고정 높이)
            Button(
                onClick = {
                    val qty = amount.toDoubleOrNull() ?: 0.0
                    if (qty > 0) {
                        orderViewModel.sell(
                            accountId = accountId,
                            symbol = symbol,
                            quantity = qty
                        )
                    }
                },
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

            Spacer(Modifier.height(12.dp))
        }
    }
}
