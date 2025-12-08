package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.bitable_fe.core.network.response.MarketData
import com.example.bitable_fe.core.ui.component.AudioPlayerUtil
import com.example.bitable_fe.core.ui.state.CoinDetailState
import com.example.bitable_fe.core.ui.viewmodel.CoinDetailViewModel
import com.example.bitable_fe.feature.trade.screen.component.BottomTradeButtons
import com.example.bitable_fe.feature.trade.screen.component.ChartPeriodTabs
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton
import com.example.bitable_fe.core.ui.state.VoiceUiState
import com.example.bitable_fe.core.ui.viewmodel.VoiceViewModel
import com.example.bitable_fe.feature.invest.screen.component.ChartBox

@Composable
fun CoinDetailScreen(
    coinName: String,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    voiceVm: VoiceViewModel = hiltViewModel(),
    onSellClick: (String) -> Unit = {},
    onBuyClick: (String) -> Unit = {}
) {
    val period = coinDetailViewModel.period
    var isFavorite by remember { mutableStateOf(false) }
    val chartAnalysis by coinDetailViewModel.chartAnalysis.collectAsState()
    val voiceState by voiceVm.state.collectAsState()

    LaunchedEffect(coinName) {
        coinDetailViewModel.loadTicker(coinName)
    }

    val uiState by coinDetailViewModel.tickerState.collectAsState()

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp)
                    .semantics{
                        contentDescription = "코인 정보"
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "코인정보",
                    fontSize = 20.sp,
                    color = Color(0xFF1A1E27),
                    fontWeight = FontWeight.Bold
                )
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
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    // ▣ 제목 + 하트
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(ticker.market.split("-")[0], fontSize = 32.sp, fontWeight = FontWeight.Bold)
                                Text(ticker.market, fontSize = 22.sp, color = Color.Gray)
                            }

                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "좋아요",
                                tint = if (isFavorite) Color(0xFFFF3A5F) else Color.Gray,
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .clickable { isFavorite = !isFavorite }
                            )
                        }
                    }

                    // ▣ 기간 탭
                    item {
                        ChartPeriodTabs(
                            selectedIndex = period,
                            onSelectedChange = { coinDetailViewModel.setPeriodTab(it) }
                        )
                    }

                    // ▣ 차트 영역(임시)
                    item {
                        val candles = coinDetailViewModel.chartState.collectAsState().value
                        val prices = candles.mapNotNull { it.trade_price }

                        ChartBox(values = prices)
                    }

                    // ▣ 차트 요약 버튼
                    item {
                        Button(
                            onClick = {
                                coinDetailViewModel.loadChartAnalysis()

                                if (chartAnalysis.isNotBlank()) {
                                    voiceVm.tts(chartAnalysis)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Color(0xFF006AFF)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("차트 요약 듣기", color = Color.White, fontSize = 18.sp,
                                modifier = Modifier.semantics{
                                    contentDescription = "차트 요약 듣기"
                                })
                        }
                    }

                    // ▣ 가격 정보 리스트 — 실제 데이터 연결됨
                    item {
                        PriceInfoList(ticker)
                    }

                    item { Spacer(Modifier.height(80.dp)) }
                }
            }
        }
    }

    LaunchedEffect(voiceState) {
        when (voiceState) {
            is VoiceUiState.Success -> {
                val audio = (voiceState as VoiceUiState.Success).data as ByteArray
                AudioPlayerUtil.playByteArray(audio)
            }
            else -> Unit
        }
    }
}


////////////////////////////////////////////////////////////////////////////////
// 🔽 회색 박스 구분용 UI
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
// 🔽 가격 정보 리스트 (차트 아래 항목들만 회색 박스로 분리)
////////////////////////////////////////////////////////////////////////////////

@Composable
fun PriceInfoList(ticker: MarketData) {
    val changeRate = ticker.change_rate * 100
    val isUp = changeRate >= 0

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        SectionItemBox {
            PriceRow(
                title = "현재가",
                value = "%,d".format(ticker.trade_price.toInt()),
                rate = String.format("%.2f%%", changeRate),
                up = isUp
            )
        }

        SectionItemBox {
            PriceRow(
                title = "24h 변동률",
                value = String.format("%.2f%%", changeRate),
                rate = "",
                up = isUp
            )
        }

        SectionItemBox {
            PriceRow(
                title = "당일 고가",
                value = "%,d".format(ticker.high_price.toInt()),
                rate = "",
                up = true
            )
            Spacer(Modifier.height(8.dp))
            PriceRow(
                title = "당일 저가",
                value = "%,d".format(ticker.low_price.toInt()),
                rate = "",
                up = false
            )
        }

        SectionItemBox {
            PriceRowOnlyText(
                title = "실시간 거래량",
                value = "%,.3f".format(ticker.trade_volume)
            )
        }
    }
}


////////////////////////////////////////////////////////////////////////////////
// 🔽 가격 row UI
////////////////////////////////////////////////////////////////////////////////

@Composable
fun PriceRow(
    title: String,
    value: String,
    rate: String,
    up: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {


        Text(
            text = title,
            fontSize = 24.sp,
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
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1E27)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rate,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (up) Color(0xFFFF3A5F) else Color(0xFF0085FF)
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = if (up) "▲" else "▼",
                    fontSize = 24.sp,
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
    Column {
        Text(title, fontSize = 16.sp, color = Color(0xFF6B7583))
        Text(
            value,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
