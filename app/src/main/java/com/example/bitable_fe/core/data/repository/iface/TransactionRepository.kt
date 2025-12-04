package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.response.TransactionResponse

interface TransactionRepository {
    suspend fun getDeposits(accountId: Long): List<TransactionResponse>
    suspend fun getWithdrawals(accountId: Long): List<TransactionResponse>
}
