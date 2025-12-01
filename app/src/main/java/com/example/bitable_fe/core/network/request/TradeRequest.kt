package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class BuyRequest(
    val accountId: Long,
    val symbol: String,
    val amountKrw: Double
)

// POST /api/orders/sell
@Serializable
data class SellRequest(
    val accountId: Long,
    val symbol: String,
    val quantity: Double
)