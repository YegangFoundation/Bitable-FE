package com.example.bitable_fe.feature.invest.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.data.model.CoinDetailUi
import com.example.bitable_fe.core.data.model.PortfolioUi
import com.example.bitable_fe.core.network.response.HoldingResponse
import com.example.bitable_fe.core.ui.viewmodel.PortfolioViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel


@Composable
fun DepositMainScreen(
    vm: PortfolioViewModel = hiltViewModel(),
    preferencesViewModel: UserPreferencesViewModel = hiltViewModel()
) {
    val ui = vm.uiState.collectAsState().value
    val accountId by preferencesViewModel.userIdFlow.collectAsState(initial = -1L)
    LaunchedEffect(accountId) {
        if (accountId != -1L){
            vm.loadPortfolio(accountId)
        }

    }

    if (ui == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    PortfolioContent(ui)
}


@Composable
fun LabeledValue(label: String, value: String, isProfit: Boolean? = null) {
    val color = when (isProfit) {
        true -> Color(0xFFE53935)
        false -> Color(0xFF1E88E5)
        else -> Color.Unspecified
    }

    Row(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, color = color, fontWeight = FontWeight.Medium, fontSize = 16.sp)
    }
}

@Composable
fun CoinDetailCard(coin: CoinDetailUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        Text(
            "${coin.name} ${coin.symbol}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // 평가 손익
        LabeledValue(
            label = "평가 손익",
            value = "%,d".format(coin.profit.toInt()),
            isProfit = coin.profit >= 0
        )

        // 수익률
        LabeledValue(
            label = "수익률",
            value = String.format("%.2f%%", coin.profitRate),
            isProfit = coin.profitRate >= 0
        )

        // 보유수량
        LabeledValue(
            label = "보유수량",
            value = "${coin.quantity} ${coin.symbol}"
        )

        // 평가금액
        LabeledValue(
            label = "평가금액",
            value = "%,d KRW".format(coin.evalAmount.toInt())
        )

        // 매수금액
        LabeledValue(
            label = "매수금액",
            value = "%,d KRW".format(coin.buyAmount.toInt())
        )
    }
}

@Composable
fun PortfolioContent(state: PortfolioUi) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text("총 보유 자산", fontSize = 16.sp, color = Color.Gray)
        Text(
            text = "%,d".format(state.totalBalance.toInt()),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("투자 내역 요약 듣기")
        }

        Spacer(Modifier.height(20.dp))
        Divider(color = Color(0xFFE5E5E5), thickness = 8.dp)
        Spacer(Modifier.height(20.dp))

        SummarySection(state)

        Spacer(Modifier.height(20.dp))
        Divider(color = Color(0xFFE5E5E5), thickness = 8.dp)
        Spacer(Modifier.height(20.dp))

        Text("보유자산 포트폴리오", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))

        PieChartSection(state)

        Spacer(Modifier.height(20.dp))

        state.coinDetails.forEach { coin ->
            CoinDetailCard(coin)
            Spacer(Modifier.height(16.dp))
        }
    }
}
@Composable
fun SummarySection(state: PortfolioUi) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("총 매수", fontSize = 16.sp, color = Color.Gray)
            Text("%,d".format(state.totalBalance.toInt()), fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("총 평가", fontSize = 16.sp, color = Color.Gray)
            Text("%,d".format(state.totalBalance.toInt()), fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("평가 손익", fontSize = 16.sp, color = Color.Gray)

            val profit = state.totalProfit
            val isUp = profit >= 0
            val color = if (isUp) Color(0xFFE53935) else Color(0xFF1E88E5)

            Row {
                Text(
                    text = "%,d".format(profit.toInt()),
                    fontSize = 16.sp,
                    color = color,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isUp) " ▲" else " ▼",
                    fontSize = 16.sp,
                    color = color
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("수익률", fontSize = 16.sp, color = Color.Gray)

            val rate = state.totalProfitRate
            val isUp = rate >= 0
            val color = if (isUp) Color(0xFFE53935) else Color(0xFF1E88E5)

            Row {
                Text(
                    text = String.format("%.2f%%", rate),
                    color = color,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isUp) " ▲" else " ▼",
                    fontSize = 16.sp,
                    color = color
                )
            }
        }
    }
}

@Composable
fun PieChartSection(state: PortfolioUi) {
    val pieData = state.pieItems

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(180.dp)) {
            var startAngle = -90f

            pieData.forEach { item ->
                val sweep = (item.ratio * 360f).toFloat()

                drawArc(
                    color = Color(item.color),
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = 40f)
                )
                startAngle += sweep
            }
        }

        // 중앙 텍스트
        Text(
            "보유비중",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

    Spacer(Modifier.height(16.dp))

    pieData.forEachIndexed { index, item ->
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(Color(item.color), shape = CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Text("${index + 1}   ${item.name}", fontSize = 16.sp)
            }

            Text(
                String.format("%.1f%%", item.ratio * 100),
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}
