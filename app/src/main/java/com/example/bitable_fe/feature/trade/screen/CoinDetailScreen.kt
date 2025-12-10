package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.network.response.CandleResponse
import com.example.bitable_fe.core.network.response.MarketData
import com.example.bitable_fe.core.ui.component.AudioPlayerUtil
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton
import com.example.bitable_fe.core.ui.state.CoinDetailState
import com.example.bitable_fe.core.ui.state.VoiceUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinDetailViewModel
import com.example.bitable_fe.core.ui.viewmodel.DailyStats
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel
import com.example.bitable_fe.feature.invest.screen.component.ChartBox
import com.example.bitable_fe.feature.trade.screen.component.BottomTradeButtons
import com.example.bitable_fe.feature.trade.screen.component.ChartPeriodTabs
import kotlinx.coroutines.delay


@Composable
fun CoinDetailScreen(
    coinName: String,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    voiceVm: VoiceViewModel = hiltViewModel(),
    onSellClick: (String) -> Unit = {},
    onBuyClick: (String) -> Unit = {}
) {
    val uiState by coinDetailViewModel.tickerState.collectAsState()
    val dailyStats by coinDetailViewModel.dailyStats.collectAsState()
    val chartAnalysis by coinDetailViewModel.chartAnalysis.collectAsState()
    val voiceState by voiceVm.state.collectAsState()

    var isFavorite by remember { mutableStateOf(false) }
    var isSpeaking by remember { mutableStateOf(false) }

    LaunchedEffect(coinName) {
        coinDetailViewModel.loadTicker(coinName)
    }

    LaunchedEffect(Unit) {
        coinDetailViewModel.clearChartAnalysis()
    }

    LaunchedEffect(chartAnalysis) {
        if (chartAnalysis.isNotBlank()) {
            voiceVm.tts(chartAnalysis)
            coinDetailViewModel.clearChartAnalysis()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            coinDetailViewModel.stopRealTimeTicker()
        }
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("코인정보", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        },
        floatingActionButton = { VoiceFloatingButton() },
        bottomBar = {
            BottomTradeButtons(
                onSellClick = { onSellClick(coinName) },
                onBuyClick = { onBuyClick(coinName) }
            )
        }
    ) { padding ->

        when (uiState) {
            is CoinDetailState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CoinDetailState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("데이터 로딩 실패 😢")
                }
            }

            is CoinDetailState.Success -> {
                val ticker = (uiState as CoinDetailState.Success).data

                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    /** --------------------------
                     *  제목 + 좋아요
                     *  -------------------------- */
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    ticker.market.split("-")[0],
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(ticker.market, fontSize = 22.sp, color = Color.Gray)
                            }

                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "좋아요",
                                tint = if (isFavorite) Color.Red else Color.Gray,
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .clickable { isFavorite = !isFavorite }
                            )
                        }
                    }

                    /** --------------------------
                     *  기간 탭
                     *  -------------------------- */
                    item {
                        ChartPeriodTabs(
                            selectedIndex = coinDetailViewModel.period,
                            onSelectedChange = { coinDetailViewModel.setPeriodTab(it) }
                        )
                    }

                    /** --------------------------
                     *  차트
                     *  -------------------------- */
                    item {
                        val candles = coinDetailViewModel.chartState.collectAsState().value
                        val prices = candles.mapNotNull { it.trade_price }
                        ChartBox(values = prices)
                    }

                    /** --------------------------
                     *  차트 요약
                     *  -------------------------- */
                    item {
                        Button(
                            onClick = { coinDetailViewModel.loadChartAnalysis() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF006AFF))
                        ) {
                            Text("차트 요약 듣기", color = Color.White, fontSize = 18.sp)
                        }
                    }

                    /** --------------------------
                     *  가격 정보 + 변동률 (실시간)
                     *  -------------------------- */
                    item {
                        PriceInfoList(
                            ticker = ticker,
                            daily = dailyStats,
                            isSpeaking = isSpeaking,
                            onToggleSpeaking = { isSpeaking = !isSpeaking }
                        )
                    }

                    item { Spacer(Modifier.height(80.dp)) }
                }
            }
        }
    }

    /** --------------------------
     *  실시간 현재가 음성
     *  -------------------------- */
    LaunchedEffect(isSpeaking) {
        if (isSpeaking) {
            while (isSpeaking) {
                val state = coinDetailViewModel.tickerState.value
                if (state is CoinDetailState.Success) {
                    voiceVm.tts("현재 가격은 ${state.data.trade_price.toInt()} 원입니다")
                }
                delay(3000)
            }
        }
    }

    /** --------------------------
     *  TTS 오디오 재생
     *  -------------------------- */
    LaunchedEffect(voiceState) {
        if (voiceState is VoiceUiState.Success) {
            AudioPlayerUtil.playByteArray(
                ((voiceState as VoiceUiState.Success).data as ByteArray)
            )
        }
    }
}


////////////////////////////////////////////////////////////////////////////////
// 🔽 가격 정보 컴포넌트
////////////////////////////////////////////////////////////////////////////////
@Composable
fun PriceInfoList(
    ticker: MarketData,
    daily: DailyStats?,
    isSpeaking: Boolean,
    onToggleSpeaking: () -> Unit
) {
    val changeRate = daily?.changeRate ?: 0.0
    val highRate = daily?.highRate ?: 0.0
    val lowRate = daily?.lowRate ?: 0.0

    val isUp = changeRate >= 0

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        // ▣ 현재가 + 실시간 듣기 버튼
        SectionItemBox {
            PriceRow(
                title = "현재가",
                value = "%,d 원".format(ticker.trade_price.toInt()),
                rate = String.format("%.2f%%", changeRate),
                up = isUp
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onToggleSpeaking,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    if (isSpeaking) Color(0xFFFF5555) else Color(0xFF2F8BFF)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    if (isSpeaking) "현재가 음성 종료" else "현재가 실시간 듣기",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        // ▣ 24h 변동률
        SectionItemBox {
            PriceRow(
                title = "24h 변동률",
                value = String.format("%.2f%%", changeRate),
                rate = String.format("%.2f%%", changeRate),
                up = changeRate >= 0
            )
        }

        // ▣ 고가 / 저가
        SectionItemBox {
            PriceRow(
                title = "고가",
                value = "%,d 원".format(ticker.high_price.toInt()),
                rate = String.format("%.2f%%", highRate),
                up = highRate >= 0
            )
            Spacer(Modifier.height(8.dp))
            PriceRow(
                title = "저가",
                value = "%,d 원".format(ticker.low_price.toInt()),
                rate = String.format("%.2f%%", lowRate),
                up = lowRate >= 0
            )
        }

        // ▣ 거래량
        SectionItemBox {
            PriceRowOnlyText(
                title = "거래량",
                value = "%,.3f".format(ticker.trade_volume)
            )
        }
    }
}




////////////////////////////////////////////////////////////////////////////////
// 🔽 회색 박스 UI
////////////////////////////////////////////////////////////////////////////////

@Composable
fun SectionItemBox(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF6F7F9), RoundedCornerShape(12.dp))
            .padding(16.dp),
        content = content
    )
}


////////////////////////////////////////////////////////////////////////////////
// 🔽 개별 가격 Row UI
////////////////////////////////////////////////////////////////////////////////
@Composable
fun PriceRow(
    title: String,
    value: String,
    rate: String,
    up: Boolean
) {
    val arrow = if (up) "상승" else "하락"
    val talkBackText = "$title, $value, $rate, $arrow"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = talkBackText }  // ★ TalkBack 문장
    ) {

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1E27)
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1E27)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = rate,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (up) Color(0xFFFF3A5F) else Color(0xFF0085FF)
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = if (up) "▲" else "▼",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (up) Color(0xFFFF3A5F) else Color(0xFF0085FF)
                )
            }
        }
    }
}



@Composable
fun PriceRowOnlyText(
    title: String,
    value: String
) {
    val talkBackText = "$title, $value"

    Column(
        modifier = Modifier.semantics {
            contentDescription = talkBackText // ★ 거래량 읽기
        }
    ) {
        Text(title, fontSize = 16.sp, color = Color(0xFF6B7583))
        Text(
            value,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
