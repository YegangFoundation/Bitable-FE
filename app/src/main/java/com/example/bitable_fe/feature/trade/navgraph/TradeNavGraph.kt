package com.example.bitable_fe.feature.trade.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.bitable_fe.core.navigation.TradeNavigator
import com.example.bitable_fe.feature.trade.screen.BuyScreen
import com.example.bitable_fe.feature.trade.screen.CoinDetailScreen
import com.example.bitable_fe.feature.trade.screen.ExchangeScreen
import com.example.bitable_fe.feature.trade.screen.SellScreen

fun NavGraphBuilder.tradeNavGraph(
    navController: NavController,
    navigator: TradeNavigator
) {
    composable<TradeRoute.ExchangeRoute> {
        ExchangeScreen(
            onCoinClick = { coinName ->
                navController.navigate(TradeRoute.CoinDetailRoute(coinName))
            },
            onHomeClick = {
                navigator.resetTradeScreen()
            },
            onInvestClick = {
                navigator.goToInvestScreen()
            },
            onSettingClick = {
                navigator.goToSettingScreen()
            }
        )
    }

    composable<TradeRoute.CoinDetailRoute> { backStackEntry ->
        val args = backStackEntry.toRoute<TradeRoute.CoinDetailRoute>()

        CoinDetailScreen(
            coinName = args.coinName,
            onBuyClick = {
                navController.navigate(TradeRoute.BuyRoute(args.coinName))
            },
            onSellClick = {
                navController.navigate(TradeRoute.SellRoute(args.coinName))
            }
        )
    }

    composable<TradeRoute.BuyRoute> { backStackEntry ->
        val args = backStackEntry.toRoute<TradeRoute.BuyRoute>()

        BuyScreen(
            symbol = args.coinName
        )
    }

    composable<TradeRoute.SellRoute> { backStackEntry ->
        val args = backStackEntry.toRoute<TradeRoute.SellRoute>()

        SellScreen(
            symbol = args.coinName
        )
    }

}