package com.example.bitable_fe.feature.invest.navgraph

import kotlinx.serialization.Serializable

sealed interface InvestRoute {
    @Serializable
    data object DepositMainRoute : InvestRoute
    @Serializable
    data object DepositAmountRoute : InvestRoute

    @Serializable
    data object KrwDetailRoute : InvestRoute

    @Serializable
    data object ProfitRoute : InvestRoute

    @Serializable
    data object PortfolioRoute : InvestRoute

    @Serializable
    data object InvestHostRoute : InvestRoute

    @Serializable
    data class DepositResultRoute(
        val amount: Long
    ) : InvestRoute
}