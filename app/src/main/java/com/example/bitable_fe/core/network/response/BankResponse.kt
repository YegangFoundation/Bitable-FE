package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class BankAccountResponse(
    val bankAccountId: Long,
    val bankName: String,
    val accountNumber: String,
    val linkedAt: String
)

@Serializable
data class MockBankAccountResponse(
    val bankName: String,
    val accountNumber: String,
    val balance: Double
)