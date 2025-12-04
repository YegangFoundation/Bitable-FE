package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.BankRepository
import com.example.bitable_fe.core.network.api.BankApi
import com.example.bitable_fe.core.network.request.LinkBankAccountRequest
import com.example.bitable_fe.core.network.response.BankAccountResponse
import com.example.bitable_fe.core.network.response.MockBankAccountResponse
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val api: BankApi
) : BankRepository {

    override suspend fun getAvailableAccounts(): List<MockBankAccountResponse> =
        api.getAvailableBankAccounts().data!!

    override suspend fun linkBankAccount(req: LinkBankAccountRequest): BankAccountResponse =
        api.linkBankAccount(req).data!!
}
