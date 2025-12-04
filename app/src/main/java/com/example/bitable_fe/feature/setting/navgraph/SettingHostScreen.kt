package com.example.bitable_fe.feature.setting.navgraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bitable_fe.core.navigation.SettingNavigator
import com.example.bitable_fe.core.ui.component.BottomBar

@Composable
fun SettingsHostScreen(
    navigator: SettingNavigator
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                selectedTab = 2, // Setting
                onHomeClick = { navigator.goToTradeScreen() },
                onInvestClick = { navigator.goToInvestScreen() },
                onSettingClick = { navigator.restSettingScreen() }
            )
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = SettingsRoute.SettingsMainRoute,
            modifier = Modifier.padding(padding)
        ) {
            settingsInnerGraph(navController)
        }
    }
}
