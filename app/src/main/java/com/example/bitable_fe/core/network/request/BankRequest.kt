package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class LinkBankAccountRequest(
    val userId: Long,
    val bankName: String,
    val accountNumber: String
)
