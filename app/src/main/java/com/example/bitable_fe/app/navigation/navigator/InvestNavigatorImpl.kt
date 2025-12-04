package com.example.bitable_fe.app.navigation.navigator

import androidx.navigation.NavController
import com.example.bitable_fe.core.navigation.InvestNavigator
import com.example.bitable_fe.feature.invest.navgraph.InvestRoute
import com.example.bitable_fe.feature.setting.navgraph.SettingsRoute
import com.example.bitable_fe.feature.trade.navgraph.TradeRoute

class InvestNavigatorImpl(
    private val navController: NavController
): InvestNavigator{
    override fun goToSettingScreen() {
        navController.navigate(SettingsRoute.SettingsHostRoute)
    }

    override fun goToTradeScreen() {
        navController.navigate(TradeRoute.ExchangeRoute)
    }

    override fun restInvestScreen() {
        navController.navigate(InvestRoute.InvestHostRoute){
            popUpTo(InvestRoute.InvestHostRoute){
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}