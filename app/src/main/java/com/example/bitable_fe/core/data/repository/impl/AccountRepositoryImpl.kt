package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.AccountRepository
import com.example.bitable_fe.core.network.api.AccountApi
import com.example.bitable_fe.core.network.request.CreateAccountRequest
import com.example.bitable_fe.core.network.request.DepositWithdrawRequest
import com.example.bitable_fe.core.network.request.ResetBalanceRequest
import com.example.bitable_fe.core.network.response.AccountInfo
import com.example.bitable_fe.core.network.response.TransactionResponse
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi
) : AccountRepository {

    override suspend fun createAccount(req: CreateAccountRequest): AccountInfo =
        api.createAccount(req).data!!

    override suspend fun getAccountsByUser(userId: Long): List<AccountInfo> =
        api.getAccountsByUser(userId).data!!

    override suspend fun getSummary(accountId: Long): AccountInfo =
        api.getAccountSummary(accountId).data!!

    override suspend fun resetBalance(accountId: Long, newBalance: Double): AccountInfo =
        api.resetBalance(accountId, ResetBalanceRequest(newBalance)).data!!

    override suspend fun deposit(accountId: Long, amount: Double, memo: String?): TransactionResponse =
        api.deposit(accountId, DepositWithdrawRequest(amount, memo)).data!!

    override suspend fun withdraw(accountId: Long, amount: Double, memo: String?): TransactionResponse =
        api.withdraw(accountId, DepositWithdrawRequest(amount, memo)).data!!
}
