package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.request.LinkBankAccountRequest
import com.example.bitable_fe.core.network.response.BankAccountResponse
import com.example.bitable_fe.core.network.response.MockBankAccountResponse


interface BankRepository {
    suspend fun getAvailableAccounts(): List<MockBankAccountResponse>
    suspend fun linkBankAccount(req: LinkBankAccountRequest): BankAccountResponse
}
