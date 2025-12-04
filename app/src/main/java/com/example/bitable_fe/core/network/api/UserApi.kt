// core/network/api/UserApi.kt
package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.CreateUserRequest
import com.example.bitable_fe.core.network.request.SetDefaultBankAccountRequest
import com.example.bitable_fe.core.network.request.UpdateSettingsRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.BankAccountResponse
import com.example.bitable_fe.core.network.response.UserResponse
import com.example.bitable_fe.core.network.response.UserSettingsResponse
import retrofit2.http.*

interface UserApi {

    @POST("/api/users")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): ApiResponse<UserResponse>

    @GET("/api/users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Long
    ): ApiResponse<UserResponse>

    @GET("/api/users/phone/{phoneNumber}")
    suspend fun getUserByPhone(
        @Path("phoneNumber") phoneNumber: String
    ): ApiResponse<UserResponse>

    @PUT("/api/users/{userId}/onboarding-complete")
    suspend fun completeOnboarding(
        @Path("userId") userId: Long
    ): ApiResponse<UserResponse>

    @GET("/api/users/{userId}/settings")
    suspend fun getSettings(
        @Path("userId") userId: Long
    ): ApiResponse<UserSettingsResponse>

    @PUT("/api/users/{userId}/settings")
    suspend fun updateSettings(
        @Path("userId") userId: Long,
        @Body request: UpdateSettingsRequest
    ): ApiResponse<UserSettingsResponse>

    @PUT("/api/users/{userId}/default-bank-account")
    suspend fun setDefaultBankAccount(
        @Path("userId") userId: Long,
        @Body request: SetDefaultBankAccountRequest
    ): ApiResponse<UserResponse>

    @GET("/api/users/{userId}/bank-accounts")
    suspend fun getUserBankAccounts(
        @Path("userId") userId: Long
    ): ApiResponse<List<BankAccountResponse>>
}
