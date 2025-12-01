package com.example.bitable_fe.feature.trade.screen.component


import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun MarketTabRow() {
    val tabs = listOf("KRW", "BTC", "USDT", "관심")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex
    ) {
        tabs.forEachIndexed { index, text ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = {
                    Text(
                        text,
                        fontSize = 20.sp,
                        color = if (selectedTabIndex == index)
                            Color(0xFF3181F4)
                        else
                            Color(0xFF333D4B)
                    )
                }
            )
        }
    }
}
