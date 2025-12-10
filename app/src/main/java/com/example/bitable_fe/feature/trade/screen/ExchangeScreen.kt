package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.network.response.MarketData
import com.example.bitable_fe.core.ui.component.BottomBar
import com.example.bitable_fe.core.ui.state.CoinUiState
import com.example.bitable_fe.core.ui.viewmodel.CoinViewModel
import com.example.bitable_fe.feature.trade.screen.component.CoinList
import com.example.bitable_fe.feature.trade.screen.component.ExchangeTopBar
import com.example.bitable_fe.feature.trade.screen.component.MarketTabRow
import com.example.bitable_fe.feature.trade.screen.component.SearchBar
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton

@Composable
fun ExchangeScreen(
    onCoinClick: (String) -> Unit,
    onHomeClick: () -> Unit,
    onInvestClick: () -> Unit,
    onSettingClick: () -> Unit,
    coinViewModel: CoinViewModel = hiltViewModel()
) {
    val uiState by coinViewModel.state.collectAsState()
    val tickers by coinViewModel.tickers.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var keyword by remember { mutableStateOf("") }

    // 최초 1회: 마켓 목록 로드
    LaunchedEffect(Unit) {
        coinViewModel.getAllMarkets()
    }

    // 마켓 목록을 불러왔다면 → 실시간 ticker 시작
    LaunchedEffect(uiState) {
        if (uiState is CoinUiState.Success<*>) {
            val markets = (uiState as CoinUiState.Success<List<MarketData>>).data
            val symbols = markets.map { it.market }
            coinViewModel.startRealTimeTicker(symbols)
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            coinViewModel.stopRealTimeTicker()
        }
    }
    Scaffold(
        topBar = { ExchangeTopBar() },
        bottomBar = {
            BottomBar(
                selectedTab = 0,
                onHomeClick = onHomeClick,
                onSettingClick = onSettingClick,
                onInvestClick = onInvestClick
            )
        },
        floatingActionButton = { VoiceFloatingButton() }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            SearchBar(
                text = keyword,
                onTextChange = { keyword = it }
            )

            Spacer(Modifier.height(12.dp))

            MarketTabRow(
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(Modifier.height(12.dp))

            when (uiState) {
                is CoinUiState.Loading -> Text("로딩 중…")

                is CoinUiState.Error ->
                    Text("에러: ${(uiState as CoinUiState.Error).msg}")

                is CoinUiState.Success -> {
                    val markets = (uiState as CoinUiState.Success<List<MarketData>>).data

                    val filtered = when (selectedTab) {
                        0 -> markets.filter { it.market.startsWith("KRW-") }
                        1 -> markets.filter { it.market.startsWith("BTC-") }
                        2 -> markets.filter { it.market.startsWith("USDT-") }
                        else -> markets
                    }

                    val searched = filtered.filter { item ->
                        val name = item.koreanName
                        val symbol = item.market.replace("-", "")
                        name.contains(keyword, ignoreCase = true) ||
                                symbol.contains(keyword, ignoreCase = true)
                    }

                    // ❗ 여기서 tickerFlow 값을 리스트 항목에 병합한다
                    val merged = searched.map { item ->
                        tickers[item.market] ?: item // ticker가 있으면 교체
                    }

                    CoinList(
                        items = merged,
                        onItemClick = onCoinClick
                    )
                }

                else -> {}
            }
        }
    }
}






@Preview
@Composable
private fun ExchangeScreenPreview(){
    ExchangeScreen({}, {}, {}, {})
}