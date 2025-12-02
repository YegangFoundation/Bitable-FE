package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterCoinRequest(
    val symbol: String,
    val name: String,
    val englishName: String? = null
)