package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MarketTabRow() {
    val tabs = listOf("KRW", "BTC", "USDT", "관심")
    var selected by remember { mutableStateOf(0) }

    SecondaryTabRow(selectedTabIndex = selected) {
        tabs.forEachIndexed { index, text ->
            Tab(
                selected = selected == index,
                onClick = { selected = index },
                text = { Text(text) }
            )
        }
    }
}
