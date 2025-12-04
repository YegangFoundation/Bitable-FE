package com.example.bitable_fe.core.data.model


data class PortfolioUi(
    val totalBalance: Double = 0.0,
    val totalProfit: Double = 0.0,
    val totalProfitRate: Double = 0.0,
    val pieItems: List<PieItemUi> = emptyList(),
    val coinDetails: List<CoinDetailUi> = emptyList()
)

data class PieItemUi(
    val name: String,
    val ratio: Double,
    val color: Long
)

data class CoinDetailUi(
    val symbol: String,
    val name: String,
    val quantity: Double,
    val profit: Double,
    val profitRate: Double,
    val evalAmount: Double,
    val buyAmount: Double
)
