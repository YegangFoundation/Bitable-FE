package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bitable_fe.core.ui.component.BottomBar
import com.example.bitable_fe.feature.trade.screen.component.CoinList
import com.example.bitable_fe.feature.trade.screen.component.ExchangeTopBar
import com.example.bitable_fe.feature.trade.screen.component.MarketTabRow
import com.example.bitable_fe.feature.trade.screen.component.SearchBar
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun ExchangeScreen(
    onCoinClick: (String) -> Unit,
    onHomeClick: () -> Unit,
    onInvestClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    Scaffold(
        topBar = { ExchangeTopBar() },
        bottomBar = { BottomBar(
            selectedTab = 0,
            onHomeClick = onHomeClick,
            onSettingClick = onSettingClick,
            onInvestClick = onInvestClick
        ) },
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
            CoinList(onCoinClick)
        }
    }
}


@Preview
@Composable
private fun ExchangeScreenPreview(){
    ExchangeScreen({}, {}, {}, {})
}