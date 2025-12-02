package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.request.UpdateSettingsRequest
import com.example.bitable_fe.core.network.response.BankAccountResponse
import com.example.bitable_fe.core.network.response.UserResponse
import com.example.bitable_fe.core.network.response.UserSettingsResponse

interface UserRepository {
    suspend fun createUser(phoneNumber: String, name: String): UserResponse
    suspend fun getUser(userId: Long): UserResponse
    suspend fun getUserByPhone(phoneNumber: String): UserResponse
    suspend fun completeOnboarding(userId: Long): UserResponse
    suspend fun setDefaultBankAccount(userId: Long, bankAccountId: Long): UserResponse
    suspend fun getBankAccounts(userId: Long): List<BankAccountResponse>
    suspend fun getSettings(userId: Long): UserSettingsResponse
    suspend fun updateSettings(userId: Long, req: UpdateSettingsRequest): UserSettingsResponse
}
