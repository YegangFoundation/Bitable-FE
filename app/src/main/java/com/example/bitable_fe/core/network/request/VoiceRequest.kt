package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class VoiceCommandRequest(
    val userId: Long,
    val command: String,
    val currentChartInterval: String? = null,
    val currentCoinSymbol: String? = null
)