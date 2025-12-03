package com.example.bitable_fe.feature.invest.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.bitable_fe.core.navigation.InvestNavigator
import com.example.bitable_fe.feature.invest.screen.DepositAmountScreen
import com.example.bitable_fe.feature.invest.screen.DepositMainScreen
import com.example.bitable_fe.feature.invest.screen.DepositResultScreen
import com.example.bitable_fe.feature.invest.screen.KrwDetailScreen
import com.example.bitable_fe.feature.invest.screen.PortfolioScreen
import com.example.bitable_fe.feature.invest.screen.ProfitScreen

fun NavGraphBuilder.investNavGraph(
    navController: NavController,
    navigator: InvestNavigator
){
    composable<InvestRoute.DepositMainRoute> {
        DepositMainScreen(
            onGoToKrwDetail = {
                navController.navigate(InvestRoute.KrwDetailRoute)
            },
            onHomeClick = {
                navigator.goToTradeScreen()
            },
            onSettingClick = {
                navigator.goToSettingScreen()
            },
            onInvestClick = {
                navigator.restInvestScreen()
            }
        )
    }

    composable<InvestRoute.KrwDetailRoute> {
        KrwDetailScreen(
            onGoDeposit = {
                navController.navigate(InvestRoute.DepositAmountRoute)
            },
            onGoWithdraw = {
                navController.navigate(InvestRoute.DepositAmountRoute)
            }
        )
    }

    composable<InvestRoute.PortfolioRoute> {
        PortfolioScreen(
            onInvestClick = {
                navigator.restInvestScreen()
            },
            onSettingClick = {
                navigator.goToSettingScreen()
            },
            onHomeClick = {
                navigator.goToTradeScreen()
            },
            onListenSummary = {
                TODO()
            }
        )
    }

    composable<InvestRoute.ProfitRoute> {
        ProfitScreen(
            onHomeClick = {
                navigator.goToTradeScreen()
            },
            onSettingClick = {
                navigator.goToSettingScreen()
            },
            onInvestClick = {
                navigator.restInvestScreen()
            },
            onListenClick = {
                TODO()
            }
        )
    }

    composable<InvestRoute.DepositAmountRoute> {
        DepositAmountScreen(
            onDeposit = { amount ->
                navController.navigate(InvestRoute.DepositResultRoute(amount.toLong()))
            }
        )
    }

    composable<InvestRoute.DepositResultRoute> { backStackEntry ->
        val args = backStackEntry.toRoute<InvestRoute.DepositResultRoute>()

        DepositResultScreen(
            amount = args.amount,
            onConfirm = {
                navigator.restInvestScreen()
            }
        )

    }
}