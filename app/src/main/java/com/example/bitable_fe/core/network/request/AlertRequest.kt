package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateAlertRequest(
    val accountId: Long,
    val symbol: String,
    val targetPrice: Double
)