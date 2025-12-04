package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.HoldingResponse
import com.example.bitable_fe.core.network.response.PortfolioSummary
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface PortfolioApi {

    @GET("/api/portfolio/summary")
    suspend fun getPortfolioSummary(
        @Query("accountId") accountId: Long
    ): ApiResponse<PortfolioSummary>

    @GET("/api/holdings/account/{accountId}")
    suspend fun getHoldingsByAccount(
        @Path("accountId") accountId: Long
    ): ApiResponse<List<HoldingResponse>>
}
