package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.request.CreateAccountRequest
import com.example.bitable_fe.core.network.response.AccountInfo
import com.example.bitable_fe.core.network.response.TransactionResponse

interface AccountRepository {
    suspend fun createAccount(req: CreateAccountRequest): AccountInfo
    suspend fun getAccountsByUser(userId: Long): List<AccountInfo>
    suspend fun getSummary(accountId: Long): AccountInfo
    suspend fun resetBalance(accountId: Long, newBalance: Double): AccountInfo
    suspend fun deposit(accountId: Long, amount: Double, memo: String? = null): TransactionResponse
    suspend fun withdraw(accountId: Long, amount: Double, memo: String? = null): TransactionResponse
}
