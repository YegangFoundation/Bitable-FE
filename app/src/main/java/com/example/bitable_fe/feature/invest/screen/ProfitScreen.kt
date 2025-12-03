package com.example.bitable_fe.feature.invest.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.ProfitViewModel
import com.example.bitable_fe.feature.invest.screen.component.ChartBox


@Composable
fun ProfitScreen(
    vm: ProfitViewModel = hiltViewModel(),
    onListenClick: () -> Unit = {}
) {
    val summary by vm.summary.collectAsState()
    val holdings by vm.holdings.collectAsState()
    val avgInvest by vm.avgInvest.collectAsState()
    val chart by vm.chartData.collectAsState()
    val period by vm.period.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadAll()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ----------------------------------
        // 제목
        // ----------------------------------
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("투자정보", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        // ----------------------------------
        // 상단 탭
        // ----------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("투자내역", color = Color.Gray)
            Text("투자손익", color = Color(0xFF006AFF), fontWeight = FontWeight.Bold)
            Text("입출금", color = Color.Gray)
        }

        Divider(color = Color(0xFF006AFF), thickness = 2.dp)

        Spacer(Modifier.height(16.dp))

        // ----------------------------------
        // 기간 탭
        // ----------------------------------
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            listOf("일", "주", "월").forEach {
                Text(
                    text = it,
                    color = if (period == it) Color.Black else Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        vm.setPeriod(it)
                        vm.generateDummyChart()
                    }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // ----------------------------------
        // 그래프 (더미 + API 반응형)
        // ----------------------------------
        ChartBox(chart)

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onListenClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFF006AFF)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("손익 리포트 요약 듣기", color = Color.White)
        }

        Spacer(Modifier.height(20.dp))

        Divider(color = Color.LightGray, thickness = 1.dp)

        Spacer(Modifier.height(20.dp))

        // ----------------------------------
        // 기간 누적 손익
        // ----------------------------------
        summary?.let { s ->
            Column {
                Text("기간 누적 손익", fontSize = 16.sp, color = Color.Gray)

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${s.profitLossKrw.toInt()}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        String.format("%.2f%%", s.profitLossRate),
                        color = if (s.profitLossRate >= 0) Color(0xFF3181F4) else Color.Red,
                        fontSize = 20.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ----------------------------------
        // 기간 평균 투자금액
        // ----------------------------------
        Column {
            Text("기간 평균 투자금액", fontSize = 16.sp, color = Color.Gray)
            Text(
                avgInvest.toInt().toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}
