package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderBookAnalysisResponse(
    val bidStrength: Double,
    val askStrength: Double,
    val imbalance: Double,
    val summary: String
)

@Serializable
data class ChartAnalysisResponse(
    val interval: String,
    val trend: String,
    val supportLevel: Double? = null,
    val resistanceLevel: Double? = null,
    val summary: String
)
