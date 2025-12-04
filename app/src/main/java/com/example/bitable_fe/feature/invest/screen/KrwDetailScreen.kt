package com.example.bitable_fe.feature.invest.screen

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
import com.example.bitable_fe.core.network.response.TransactionResponse
import com.example.bitable_fe.core.ui.state.AccountUiState
import com.example.bitable_fe.core.ui.viewmodel.DepositViewModel
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun KrwDetailScreen(
    vm: DepositViewModel = hiltViewModel(),
    onGoDeposit: () -> Unit,
    onGoWithdraw: () -> Unit
) {
    val summaryState by vm.summary.collectAsState()
    val depositLog by vm.depositLog.collectAsState()
    val withdrawLog by vm.withdrawLog.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {

        // -------------------------------------------------------------
        // ▣ 상단 Summary
        // -------------------------------------------------------------
        item {
            when (summaryState) {

                AccountUiState.Idle -> {
                    Text("정보 로딩 전...", color = Color.Gray)
                }

                AccountUiState.Loading -> {
                    Text("불러오는 중...", color = Color.Gray)
                }

                is AccountUiState.Error -> {
                    Text(
                        (summaryState as AccountUiState.Error).msg,
                        color = Color.Red
                    )
                }

                is AccountUiState.Success -> {
                    val info = (summaryState as AccountUiState.Success).data as AccountInfo

                    Text("총 보유 자산", fontSize = 20.sp)
                    Text(
                        "${info.balanceKrw.toInt()} KRW",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = onGoDeposit,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF006AFF)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("입금")
                        }

                        Spacer(Modifier.width(12.dp))

                        Button(
                            onClick = onGoWithdraw,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(Color.Gray),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("출금")
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(24.dp)) }

        // -------------------------------------------------------------
        // ▣ 입금 로그
        // -------------------------------------------------------------
        if (depositLog.isNotEmpty()) {
            item {
                Text(
                    "입금 내역",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(depositLog) { log ->
                TransactionLogRow(title = "입금 완료", color = Color(0xFF006AFF), log)
            }
        }

        // -------------------------------------------------------------
        // ▣ 출금 로그
        // -------------------------------------------------------------
        if (withdrawLog.isNotEmpty()) {
            item {
                Text(
                    "출금 내역",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(withdrawLog) { log ->
                TransactionLogRow(title = "출금 완료", color = Color(0xFFD73D4A), log)
            }
        }
    }
}


@Composable
private fun TransactionLogRow(
    title: String,
    color: Color,
    log: TransactionResponse
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(title, color = color, fontWeight = FontWeight.Bold)
        Text("${log.amount.toInt()} KRW", fontSize = 20.sp)
        Text(log.createdAt, fontSize = 14.sp, color = Color.Gray)

        Divider(Modifier.padding(vertical = 12.dp))
    }
}
