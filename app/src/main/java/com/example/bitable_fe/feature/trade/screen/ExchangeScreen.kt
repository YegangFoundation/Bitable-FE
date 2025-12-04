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
            MarketTabRow()
            Spacer(Modifier.height(12.dp))

            when (uiState) {
                is CoinUiState.Loading -> {
                    Text("로딩 중…")
                }

                is CoinUiState.Error -> {
                    Text("에러: ${(uiState as CoinUiState.Error).msg}")
                }

                is CoinUiState.Success -> {
                    val markets = (uiState as CoinUiState.Success<*>).data
                    CoinList(items = markets as List<MarketData>, onItemClick = onCoinClick)
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