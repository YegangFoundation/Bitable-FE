package com.example.bitable_fe.feature.invest.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.data.model.CoinDetailUi
import com.example.bitable_fe.core.data.model.PortfolioUi
import com.example.bitable_fe.core.ui.component.AudioPlayerUtil
import com.example.bitable_fe.core.ui.state.VoiceUiState
import com.example.bitable_fe.core.ui.viewmodel.PortfolioViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel

@Composable
fun DepositMainScreen(
    vm: PortfolioViewModel = hiltViewModel(),
    preferencesViewModel: UserPreferencesViewModel = hiltViewModel(),
    voiceVm: VoiceViewModel = hiltViewModel()
) {
    val ui = vm.uiState.collectAsState().value
    val summary by vm.historySummary.collectAsState()
    val accountId by preferencesViewModel.userIdFlow.collectAsState(initial = -1L)
    val voiceState by voiceVm.state.collectAsState()

    // 초기 로드
    LaunchedEffect(accountId) {
        if (accountId != -1L) {
            vm.loadAll(accountId)
        }
    }

    // 로딩 처리
    if (ui == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // 음성 TTS 재생 처리
    LaunchedEffect(voiceState) {
        when (voiceState) {
            is VoiceUiState.Success -> {
                val audio = (voiceState as VoiceUiState.Success).data as ByteArray
                AudioPlayerUtil.playByteArray(audio)

                voiceVm.clearState()
            }
            else -> Unit
        }
    }

    PortfolioContent(
        ui = ui,
        summary = summary,
        onListenClick = {
            if (summary.isNotBlank()) {
                voiceVm.tts(summary)
            }
        }
    )
}

@Composable
fun PortfolioContent(
    ui: PortfolioUi,
    summary: String,
    onListenClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text("총 보유 자산", fontSize = 16.sp, color = Color.Gray)
        Text(
            text = "%,d".format(ui.totalBalance.toInt()),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 🔊 요약 듣기 버튼
        Button(
            onClick = onListenClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("투자 내역 요약 듣기")
        }

        Spacer(Modifier.height(20.dp))
        Divider(color = Color(0xFFE5E5E5), thickness = 8.dp)
        Spacer(Modifier.height(20.dp))

        SummarySection(ui)

        Spacer(Modifier.height(20.dp))
        Divider(color = Color(0xFFE5E5E5), thickness = 8.dp)
        Spacer(Modifier.height(20.dp))

        Text("보유자산 포트폴리오", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))

        PieChartSection(ui)

        Spacer(Modifier.height(20.dp))

        ui.coinDetails.forEach { coin ->
            CoinDetailCard(coin)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SummarySection(state: PortfolioUi) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // ----- 총 매수 -----
        val totalBuyStr = "%,d원".format(state.totalBalance.toInt())

        Row(
            Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "총 매수 $totalBuyStr"
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("총 매수", fontSize = 16.sp, color = Color.Gray)
            Text(totalBuyStr, fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        // ----- 총 평가 -----
        val totalEvalStr = "%,d원".format(state.totalBalance.toInt())

        Row(
            Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "총 평가 $totalEvalStr"
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("총 평가", fontSize = 16.sp, color = Color.Gray)
            Text(totalEvalStr, fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        // ----- 평가 손익 -----
        val profit = state.totalProfit
        val isUp = profit >= 0
        val profitColor = if (isUp) Color(0xFFE53935) else Color(0xFF1E88E5)
        val profitStr = "%,d원".format(profit.toInt())
        val profitIndicator = if (isUp) "상승" else "하락"

        Row(
            Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "평가 손익 $profitStr $profitIndicator"
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("평가 손익", fontSize = 16.sp, color = Color.Gray)

            Row {
                Text(
                    profitStr,
                    fontSize = 16.sp,
                    color = profitColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    if (isUp) " ▲" else " ▼",
                    fontSize = 16.sp,
                    color = profitColor
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // ----- 수익률 -----
        val rate = state.totalProfitRate
        val rateStr = String.format("%.2f%%", rate)
        val isRateUp = rate >= 0
        val rateIndicator = if (isRateUp) "상승" else "하락"
        val rateColor = if (isRateUp) Color(0xFFE53935) else Color(0xFF1E88E5)

        Row(
            Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "수익률 $rateStr $rateIndicator"
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("수익률", fontSize = 16.sp, color = Color.Gray)

            Row {
                Text(
                    rateStr,
                    color = rateColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    if (isRateUp) " ▲" else " ▼",
                    fontSize = 16.sp,
                    color = rateColor
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

        LabeledValue(
            label = "평가 손익",
            value = "%,d".format(coin.profit.toInt()),
            isProfit = coin.profit >= 0
        )

        LabeledValue(
            label = "수익률",
            value = String.format("%.2f%%", coin.profitRate),
            isProfit = coin.profitRate >= 0
        )

        LabeledValue(
            label = "보유수량",
            value = "${coin.quantity} ${coin.symbol}"
        )

        LabeledValue(
            label = "평가금액",
            value = "%,d KRW".format(coin.evalAmount.toInt())
        )

        LabeledValue(
            label = "매수금액",
            value = "%,d KRW".format(coin.buyAmount.toInt())
        )
    }
}

@Composable
fun LabeledValue(label: String, value: String, isProfit: Boolean? = null) {

    // ------ 색상 (기존 유지) ------
    val color = when (isProfit) {
        true -> Color(0xFFE53935)
        false -> Color(0xFF1E88E5)
        else -> Color.Unspecified
    }

    // ------ TalkBack이 듣는 문장 구성 ------
    val profitIndicator = when (isProfit) {
        true -> "상승"
        false -> "하락"
        else -> ""   // 수익 항목이 아닐 때는 빈 문자열
    }

    // 최종 접근성 문장
    val talkbackText =
        if (profitIndicator.isNotEmpty())
            "$label $value $profitIndicator"
        else
            "$label $value"

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .semantics {     // ★ TalkBack 문장 지정
                contentDescription = talkbackText
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, color = color, fontWeight = FontWeight.Medium, fontSize = 16.sp)
    }
}
