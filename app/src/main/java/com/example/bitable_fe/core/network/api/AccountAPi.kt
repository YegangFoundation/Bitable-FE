package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.CreateAccountRequest
import com.example.bitable_fe.core.network.request.DepositWithdrawRequest
import com.example.bitable_fe.core.network.request.ResetBalanceRequest
import com.example.bitable_fe.core.network.response.AccountInfo
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.TransactionResponse
import retrofit2.http.*

interface AccountApi {

    @POST("/api/accounts")
    suspend fun createAccount(
        @Body request: CreateAccountRequest
    ): ApiResponse<AccountInfo>

    @GET("/api/accounts/user/{userId}")
    suspend fun getAccountsByUser(
        @Path("userId") userId: Long
    ): ApiResponse<List<AccountInfo>>

    @GET("/api/accounts/{accountId}/summary")
    suspend fun getAccountSummary(
        @Path("accountId") accountId: Long
    ): ApiResponse<AccountInfo>

    @POST("/api/accounts/{accountId}/deposit")
    suspend fun deposit(
        @Path("accountId") accountId: Long,
        @Body request: DepositWithdrawRequest
    ): ApiResponse<TransactionResponse>

    @POST("/api/accounts/{accountId}/withdraw")
    suspend fun withdraw(
        @Path("accountId") accountId: Long,
        @Body request: DepositWithdrawRequest
    ): ApiResponse<TransactionResponse>

    @POST("/api/accounts/{accountId}/reset-balance")
    suspend fun resetBalance(
        @Path("accountId") accountId: Long,
        @Body request: ResetBalanceRequest
    ): ApiResponse<AccountInfo>
}
