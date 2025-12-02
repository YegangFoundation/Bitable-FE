package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val transactionId: Long,
    val accountId: Long,
    val type: String, // "DEPOSIT" / "WITHDRAW"
    val amount: Double,
    val balanceAfter: Double,
    val createdAt: String
)

@Serializable
data class CoinProfitDetail(
    val symbol: String,
    val coinName: String,
    val holdingQuantity: Double,
    val avgBuyPrice: Double,
    val currentPrice: Double,
    val profitLossKrw: Double,
    val profitLossRate: Double
)

@Serializable
data class PortfolioSummary(
    val totalBalanceKrw: Double,
    val totalProfitLossKrw: Double,
    val totalProfitLossRate: Double,
    val details: List<CoinProfitDetail> = emptyList()
)

@Serializable
data class MarketBriefingResponse(
    val summary: String,
    val topGainers: List<String> = emptyList(),
    val topLosers: List<String> = emptyList()
)

@Serializable
data class HoldingResponse(
    val symbol: String,
    val coinName: String,
    val quantity: Double,
    val avgBuyPrice: Double,
    val currentPrice: Double,
    val profitLossKrw: Double,
    val profitLossRate: Double
)
