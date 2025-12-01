package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ChartPeriodTabs(
    selectedIndex: Int,
    onSelectedChange: (Int) -> Unit
) {
    val tabs = listOf("분", "일", "주", "월", "년")

    SecondaryTabRow(selectedTabIndex = selectedIndex) {
        tabs.forEachIndexed { i, text ->
            Tab(
                selected = i == selectedIndex,
                onClick = { onSelectedChange(i) },
                text = {
                    Text(
                        text,
                        fontWeight = if (selectedIndex == i) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}
