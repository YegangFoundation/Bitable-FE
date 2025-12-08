package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioHistoryListResponse(
    val summary: String? = null,
    val history: List<PortfolioHistoryResponse> = emptyList()
)

@Serializable
data class PortfolioHistoryResponse(
    val timestamp: String,
    val totalBalanceKrw: Double
)
