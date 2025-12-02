package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionApi {

    @GET("/api/transactions/deposits")
    suspend fun getDeposits(
        @Query("accountId") accountId: Long
    ): ApiResponse<List<TransactionResponse>>

    @GET("/api/transactions/withdrawals")
    suspend fun getWithdrawals(
        @Query("accountId") accountId: Long
    ): ApiResponse<List<TransactionResponse>>
}
