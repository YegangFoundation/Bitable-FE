package com.example.bitable_fe.app.navigation.navigator

import androidx.navigation.NavController
import com.example.bitable_fe.core.navigation.TradeNavigator
import com.example.bitable_fe.feature.trade.navgraph.TradeRoute

class TradeNavigatorImpl(
    private val navController: NavController
) : TradeNavigator{
    override fun goToInvestScreen() {
        TODO("Not yet implemented")
    }

    override fun goToSettingScreen() {
        TODO("Not yet implemented")
    }

    override fun resetTradeScreen() {
        navController.navigate(
            TradeRoute.ExchangeRoute
        ){
            popUpTo(TradeRoute.ExchangeRoute) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}