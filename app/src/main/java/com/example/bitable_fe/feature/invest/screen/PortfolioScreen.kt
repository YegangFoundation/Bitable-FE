package com.example.bitable_fe.feature.invest.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.component.BottomBar
import com.example.bitable_fe.core.ui.viewmodel.PortfolioViewModel
import com.example.bitable_fe.feature.invest.screen.component.PieChart
import com.example.bitable_fe.feature.invest.screen.component.PortfolioHoldingRow
import com.example.bitable_fe.feature.invest.screen.component.PortfolioSummaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(
    vm: PortfolioViewModel = hiltViewModel(),
    onListenSummary: () -> Unit = {}
) {
    val summary by remember { vm::summary }
    val holdings by remember { vm::holdings }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // 총 보유 자산
        item {
            Column {
                Text("총 보유 자산", fontSize = 18.sp, color = Color.Gray)
                Text(
                    summary!!.totalBalanceKrw.toInt().toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = onListenSummary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF006AFF))
                ) {
                    Text("투자 내역 요약 듣기", color = Color.White, fontSize = 18.sp)
                }
            }
        }

        // 총 매수 / 평가 / 손익 / 수익률
        item {
            PortfolioSummaryCard(summary = summary!!)
        }

        // 파이 차트
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("보유자산 포트폴리오", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))

                PieChart(
                    data = holdings.map { it.quantity * it.currentPrice },
                    labels = holdings.map { it.symbol }
                )
            }
        }

        // 보유자산 리스트
        items(holdings) { h ->
            PortfolioHoldingRow(h)
        }

        item { Spacer(Modifier.height(40.dp)) }
    }
}
