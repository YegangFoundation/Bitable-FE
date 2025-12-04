package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.UserRepository
import com.example.bitable_fe.core.network.api.UserApi
import com.example.bitable_fe.core.network.request.CreateUserRequest
import com.example.bitable_fe.core.network.request.SetDefaultBankAccountRequest
import com.example.bitable_fe.core.network.request.UpdateSettingsRequest
import com.example.bitable_fe.core.network.response.BankAccountResponse
import com.example.bitable_fe.core.network.response.UserResponse
import com.example.bitable_fe.core.network.response.UserSettingsResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun createUser(phoneNumber: String, name: String): UserResponse {
        return userApi.createUser(CreateUserRequest(phoneNumber, name)).data!!
    }

    override suspend fun getUser(userId: Long): UserResponse {
        return userApi.getUser(userId).data!!
    }

    override suspend fun getUserByPhone(phoneNumber: String): UserResponse {
        return userApi.getUserByPhone(phoneNumber).data!!
    }

    override suspend fun completeOnboarding(userId: Long): UserResponse {
        return userApi.completeOnboarding(userId).data!!
    }

    override suspend fun setDefaultBankAccount(userId: Long, bankAccountId: Long): UserResponse {
        return userApi.setDefaultBankAccount(userId, SetDefaultBankAccountRequest(bankAccountId)).data!!
    }

    override suspend fun getBankAccounts(userId: Long): List<BankAccountResponse> {
        return userApi.getUserBankAccounts(userId).data!!
    }

    override suspend fun getSettings(userId: Long): UserSettingsResponse {
        return userApi.getSettings(userId).data!!
    }

    override suspend fun updateSettings(userId: Long, req: UpdateSettingsRequest): UserSettingsResponse {
        return userApi.updateSettings(userId, req).data!!
    }
}
