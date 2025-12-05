package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    var selectedTab by remember { mutableIntStateOf(0) } // ← Tab 상태 추가

    LaunchedEffect(Unit) {
        coinViewModel.getAllMarkets()
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
            SearchBar()
            Spacer(Modifier.height(12.dp))

            // 🔥 이제 선택을 상위에서 관리한다!
            MarketTabRow(
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(Modifier.height(12.dp))

            when (uiState) {
                is CoinUiState.Loading -> {
                    Text("로딩 중…")
                }

                is CoinUiState.Error -> {
                    Text("에러: ${(uiState as CoinUiState.Error).msg}")
                }

                is CoinUiState.Success -> {
                    val markets = (uiState as CoinUiState.Success<List<MarketData>>).data

                    val filtered = when (selectedTab) {
                        0 -> markets.filter { it.market.startsWith("KRW-") }
                        1 -> markets.filter { it.market.startsWith("BTC-") }
                        2 -> markets.filter { it.market.startsWith("USDT-") }
                        3 -> emptyList() // 관심 코인 (추후 구현)
                        else -> markets
                    }

                    CoinList(items = filtered, onItemClick = onCoinClick)
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