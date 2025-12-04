package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    val userId: Long,
    val accountName: String,
    val initialBalance: Double? = null
)
@Serializable
data class DepositWithdrawRequest(
    val amount: Double,
    val memo: String? = null
)

@Serializable
data class ResetBalanceRequest(
    val newBalance: Double
)