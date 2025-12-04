package com.example.bitable_fe.feature.invest.navgraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bitable_fe.core.navigation.InvestNavigator
import com.example.bitable_fe.core.ui.component.BottomBar
import com.example.bitable_fe.feature.invest.screen.component.InvestTopBar
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun InvestHostScreen(navigator: InvestNavigator) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.toRoute<InvestRoute>()
    val showGlobalTopBar = currentRoute is InvestRoute.PortfolioRoute ||
            currentRoute is InvestRoute.ProfitRoute ||
            currentRoute is InvestRoute.DepositMainRoute

    Scaffold(
        topBar = {
            if (showGlobalTopBar){
                InvestTopBar(
                    selected = currentRoute,
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            popUpTo<InvestRoute.DepositMainRoute>()
                            launchSingleTop = true
                        }
                    }
                )
            }
        },
        bottomBar = {
            BottomBar(
                onInvestClick = {
                    navigator.restInvestScreen()
                },
                onSettingClick = {
                    navigator.goToSettingScreen()
                },
                onHomeClick = {
                    navigator.goToTradeScreen()
                },
                selectedTab = 2
            )
        },
        floatingActionButton = {
            VoiceFloatingButton()
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = InvestRoute.DepositMainRoute,
            modifier = Modifier.padding(padding)
        ) {
            investNavGraph(navController, navigator)
        }
    }
}
