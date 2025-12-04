package com.example.bitable_fe.app.navigation.navigator

import androidx.navigation.NavController
import com.example.bitable_fe.core.navigation.TradeNavigator
import com.example.bitable_fe.feature.invest.navgraph.InvestRoute
import com.example.bitable_fe.feature.setting.navgraph.SettingsRoute
import com.example.bitable_fe.feature.trade.navgraph.TradeRoute

class TradeNavigatorImpl(
    private val navController: NavController
) : TradeNavigator{
    override fun goToInvestScreen() {
        navController.navigate(InvestRoute.InvestHostRoute)
    }

    override fun goToSettingScreen() {
        navController.navigate(SettingsRoute.SettingsHostRoute)
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