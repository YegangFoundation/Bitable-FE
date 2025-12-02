package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.LinkBankAccountRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.BankAccountResponse
import com.example.bitable_fe.core.network.response.MockBankAccountResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BankApi {

    @POST("/api/bank/link")
    suspend fun linkBankAccount(
        @Body request: LinkBankAccountRequest
    ): ApiResponse<BankAccountResponse>

    @GET("/api/bank/accounts")
    suspend fun getAvailableBankAccounts(
        // 아마 유저 ID는 JWT에서; 필요하면 쿼리/헤더로 추가
    ): ApiResponse<List<MockBankAccountResponse>>
}