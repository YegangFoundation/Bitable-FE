package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.TransactionRepository
import com.example.bitable_fe.core.network.api.TransactionApi
import com.example.bitable_fe.core.network.response.TransactionResponse
import javax.inject.Inject


class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi
) : TransactionRepository {

    override suspend fun getDeposits(accountId: Long): List<TransactionResponse> =
        api.getDeposits(accountId).data!!

    override suspend fun getWithdrawals(accountId: Long): List<TransactionResponse> =
        api.getWithdrawals(accountId).data!!
}
