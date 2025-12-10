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
import com.example.bitable_fe.core.ui.component.AudioPlayerUtil
import com.example.bitable_fe.core.ui.state.CoinUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinViewModel
import com.example.bitable_fe.feature.trade.screen.component.PercentSelector
import com.example.bitable_fe.feature.trade.screen.component.TradeInputRow
import com.example.bitable_fe.feature.trade.screen.component.TradeNumberPad
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton
import com.example.bitable_fe.core.ui.state.CoinDetailState
import com.example.bitable_fe.core.ui.state.OrderUiState
import com.example.bitable_fe.core.ui.state.VoiceUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinDetailViewModel
import com.example.bitable_fe.core.ui.viewmodel.OrderViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel


@Composable
fun BuyScreen(
    symbol: String,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    userPreferencesViewModel: UserPreferencesViewModel = hiltViewModel(),
    voiceViewModel: VoiceViewModel = hiltViewModel()
) {
    val uiState by coinDetailViewModel.tickerState.collectAsState()
    val orderState by orderViewModel.state.collectAsState()
    val voiceState by voiceViewModel.state.collectAsState()

    // 유저 입력값
    var amount by remember { mutableStateOf("") }   // 수량
    var price by remember { mutableDoubleStateOf(0.0) }   // 현재가
    var total by remember { mutableDoubleStateOf(0.0) }   // 총액
    var showDialog by remember { mutableStateOf(false) }

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

            // 🔊 주문 체결 음성 출력
            voiceViewModel.tts("주문이 체결되었습니다")

            // 팝업 열기
            showDialog = true
        }
    }

    LaunchedEffect(voiceState) {
        if (voiceState is VoiceUiState.Success) {
            AudioPlayerUtil.playByteArray(
                (voiceState as VoiceUiState.Success).data as ByteArray
            )
            voiceViewModel.clearState()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("확인")
                }
            },
            title = { Text("주문 완료") },
            text = { Text("주문이 성공적으로 체결되었습니다.") },
            shape = RoundedCornerShape(12.dp)
        )
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
                    .weight(1.8f)              // ⭐ 중요: 비율 분배
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
                    .weight(0.4f)              // ⭐ 조정 가능
            ) {
                PercentSelector { percent ->
                    val pct = percent.replace("%", "").toInt()
                    val ratio = pct / 100.0
                    amount = ratio.toString()
                    total = price * ratio
                }
            }

            Spacer(Modifier.height(4.dp))

            // 🔹 숫자 키패드 (가장 큰 비율)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.1f)              // ⭐ 키패드 영역 크게
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
        }
    }
}
