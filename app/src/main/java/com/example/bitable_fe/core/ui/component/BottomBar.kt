package com.example.bitable_fe.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomBar(
    onHomeClick: () -> Unit,
    onTradeClick: () -> Unit,
    onSettingClick: () -> Unit,
    selectedTab: Int
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("홈") }
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = onTradeClick,
            icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, null) },
            label = { Text("거래") }
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = onSettingClick,
            icon = { Icon(Icons.Default.Settings, null) },
            label = { Text("설정") }
        )
    }
}
