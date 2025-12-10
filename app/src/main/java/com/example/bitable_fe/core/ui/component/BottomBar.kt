package com.example.bitable_fe.core.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    onHomeClick: () -> Unit,
    onInvestClick: () -> Unit,
    onSettingClick: () -> Unit,
    selectedTab: Int
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, null, modifier = Modifier.size(40.dp)) }
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = onInvestClick,
            icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, null, modifier = Modifier.size(40.dp)) },
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = onSettingClick,
            icon = { Icon(Icons.Default.Settings, null, modifier = Modifier.size(40.dp)) },
        )
    }
}
