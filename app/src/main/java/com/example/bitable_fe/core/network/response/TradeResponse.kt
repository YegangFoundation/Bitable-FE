package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val orderId: Long,
    val accountId: Long,
    val symbol: String,
    val coinName: String? = null,
    val side: String, // "BUY" or "SELL"
    val status: String, // "PENDING", "COMPLETED", "FAILED"
    val quantity: Double,
    val priceKrw: Double,
    val totalAmountKrw: Double,
    val createdAt: String,
    val completedAt: String? = null
)