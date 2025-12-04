package com.example.bitable_fe.app.navigation.navigator

import androidx.navigation.NavController
import com.example.bitable_fe.core.navigation.OnboardingNavigator
import com.example.bitable_fe.feature.trade.navgraph.TradeRoute

class OnboardingNavigatorImpl (
    private val navController: NavController
) : OnboardingNavigator{
    override fun goToTradeScreen() {
        navController.navigate(TradeRoute.ExchangeRoute)
    }
}