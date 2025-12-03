package com.example.bitable_fe.feature.invest.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.network.response.AccountInfo
import com.example.bitable_fe.core.network.response.HoldingResponse
import com.example.bitable_fe.core.ui.state.AccountUiState
import com.example.bitable_fe.core.ui.state.HoldingsUiState
import com.example.bitable_fe.core.ui.viewmodel.DepositViewModel
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun DepositMainScreen(
    vm: DepositViewModel = hiltViewModel(),
    onGoToKrwDetail: () -> Unit,
    onListen: () -> Unit
) {
    val summaryState by vm.summary.collectAsState()
    val holdingsState by vm.holdings.collectAsState()

    Scaffold(
        floatingActionButton = { VoiceFloatingButton() }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // ----------------------------------------------------
            // ▣ Summary Section
            // ----------------------------------------------------
            item {
                when (summaryState) {

                    AccountUiState.Idle -> {
                        // 화면 들어오자마자 자동 호출되면 보여줄 필요 없음
                    }

                    AccountUiState.Loading -> {
                        Text("불러오는 중...", color = Color.Gray)
                    }

                    is AccountUiState.Error -> {
                        Text(
                            text = (summaryState as AccountUiState.Error).msg,
                            color = Color.Red
                        )
                    }

                    is AccountUiState.Success -> {
                        val data = (summaryState as AccountUiState.Success).data as AccountInfo

                        Text("총 보유 자산", fontSize = 18.sp, color = Color.Gray)

                        Text(
                            "${data.balanceKrw.toInt()} KRW",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = onGoToKrwDetail,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF006AFF)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("입금", color = Color.White)
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(16.dp)) }

            // ----------------------------------------------------
            // ▣ 원화 섹션
            // ----------------------------------------------------
            item {
                Text("원화", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onGoToKrwDetail() }
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("KRW", fontSize = 20.sp)
                    Text(">", fontSize = 24.sp)
                }
            }

            item { Spacer(Modifier.height(12.dp)) }

            // ----------------------------------------------------
            // ▣ 보유 코인 리스트
            // ----------------------------------------------------
            when (holdingsState) {

                HoldingsUiState.Idle -> {
                    // 초기 상태 → 출력 안함
                }

                HoldingsUiState.Loading -> {
                    item {
                        Text("보유 코인 불러오는 중...", color = Color.Gray)
                    }
                }

                is HoldingsUiState.Error -> {
                    item {
                        Text(
                            text = (holdingsState as HoldingsUiState.Error).msg,
                            color = Color.Red
                        )
                    }
                }

                is HoldingsUiState.Success -> {
                    val list = (holdingsState as HoldingsUiState.Success<List<HoldingResponse>>).data

                    items(list) { item ->
                        CoinHoldingRow(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun CoinHoldingRow(item: HoldingResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: 코인 상세 이동 */ }
            .padding(vertical = 12.dp)
    ) {
        // BTC 비트코인
        Text("${item.coinName} (${item.symbol})", fontSize = 20.sp)

        // 0.23 (73,000 KRW)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("${item.quantity}", fontSize = 16.sp)
            Text("(${item.currentPrice.toInt()} KRW)", color = Color.Gray)
        }
    }
    Divider()
}
