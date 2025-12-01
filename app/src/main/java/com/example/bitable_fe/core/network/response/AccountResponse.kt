package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AccountInfo(
    val accountId: Long,
    val userId: Long,
    val accountName: String,
    val balanceKrw: Double,
    val initialBalanceKrw: Double,
    val profitLossKrw: Double,
    val profitLossRate: Double,
    val createdAt: String,
    val updatedAt: String
)