package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PriceAlertResponse(
    val alertId: Long,
    val accountId: Long,
    val symbol: String,
    val targetPrice: Double,
    val active: Boolean,
    val createdAt: String
)