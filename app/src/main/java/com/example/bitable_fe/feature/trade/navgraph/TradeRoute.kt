package com.example.bitable_fe.feature.trade.navgraph

import kotlinx.serialization.Serializable

sealed interface TradeRoute {
    @Serializable
    data object ExchangeRoute

    @Serializable
    data class CoinDetailRoute(
        val coinName: String
    )

    @Serializable
    data class BuyRoute(
        val coinName: String
    )

    @Serializable
    data class SellRoute(
        val coinName: String
    )
}