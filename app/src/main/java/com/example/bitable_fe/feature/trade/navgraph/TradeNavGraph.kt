package com.example.bitable_fe.feature.trade.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bitable_fe.core.navigation.TradeNavigator
import com.example.bitable_fe.feature.trade.screen.ExchangeScreen

fun NavGraphBuilder.tradeNavGraph(
    navController: NavController,
    tradeNavigator: TradeNavigator
){
    composable<TradeRoute.ExchangeRoute> {
        ExchangeScreen(
        )
    }

}