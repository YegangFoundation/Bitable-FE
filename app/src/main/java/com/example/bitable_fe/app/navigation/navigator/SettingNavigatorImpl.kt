package com.example.bitable_fe.app.navigation.navigator

import androidx.navigation.NavController
import com.example.bitable_fe.core.navigation.SettingNavigator
import com.example.bitable_fe.feature.invest.navgraph.InvestRoute
import com.example.bitable_fe.feature.setting.navgraph.SettingsRoute
import com.example.bitable_fe.feature.trade.navgraph.TradeRoute

class SettingNavigatorImpl(
    private val navController: NavController
) : SettingNavigator{
    override fun goToInvestScreen() {
        navController.navigate(InvestRoute.InvestHostRoute)
    }

    override fun goToTradeScreen() {
        navController.navigate(TradeRoute.ExchangeRoute)
    }

    override fun restSettingScreen() {
        navController.navigate(
            SettingsRoute.SettingsHostRoute
        ){
            popUpTo(SettingsRoute.SettingsHostRoute) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}