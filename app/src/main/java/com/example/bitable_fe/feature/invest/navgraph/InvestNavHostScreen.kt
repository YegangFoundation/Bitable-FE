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
import com.example.bitable_fe.core.ui.component.VoiceFloatingButton

@Composable
fun InvestHostScreen(navigator: InvestNavigator) {

    val navController = rememberNavController()

    val routeString = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    val showGlobalTopBar = when(routeString) {
        InvestRoute.PortfolioRoute::class.qualifiedName -> true
        InvestRoute.ProfitRoute::class.qualifiedName -> true
        InvestRoute.DepositMainRoute::class.qualifiedName -> true
        else -> false
    }
    val selectedRoute: InvestRoute? = when (routeString) {
        InvestRoute.PortfolioRoute::class.qualifiedName -> InvestRoute.PortfolioRoute
        InvestRoute.ProfitRoute::class.qualifiedName -> InvestRoute.ProfitRoute
        InvestRoute.DepositMainRoute::class.qualifiedName -> InvestRoute.DepositMainRoute
        else -> null
    }

    Scaffold(
        topBar = {
            if (showGlobalTopBar) {
                InvestTopBar(
                    selected = selectedRoute, // 이제 toRoute 사용 안 함
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
                onInvestClick = navigator::restInvestScreen,
                onSettingClick = navigator::goToSettingScreen,
                onHomeClick = navigator::goToTradeScreen,
                selectedTab = 1
            )
        },
        floatingActionButton = { VoiceFloatingButton() }
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
