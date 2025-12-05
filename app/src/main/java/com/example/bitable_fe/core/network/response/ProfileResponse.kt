package com.example.bitable_fe.core.network.response

import kotlinx.serialization.SerialName
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

    // 서버 필드명: quantity
    @SerialName("quantity")
    val holdingQuantity: Double,

    val avgBuyPrice: Double,
    val currentPrice: Double,

    val investedKrw: Double,
    val currentValueKrw: Double,

    val profitLossKrw: Double,
    val profitLossRate: Double
)
@Serializable
data class PortfolioSummary(
    val accountId: Long,

    // 서버 필드명: totalCurrentValueKrw
    @SerialName("totalCurrentValueKrw")
    val totalBalanceKrw: Double,

    val totalInvestedKrw: Double,
    val totalProfitLossKrw: Double,
    val totalProfitLossRate: Double,

    val calculatedAt: String,

    // 서버 필드명: coinDetails
    @SerialName("coinDetails")
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
