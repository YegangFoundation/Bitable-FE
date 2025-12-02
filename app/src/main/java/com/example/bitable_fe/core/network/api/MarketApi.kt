package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.MarketBriefingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {

    @GET("/api/market/briefing")
    suspend fun getMarketBriefing(
        @Query("userId") userId: Long? = null
    ): ApiResponse<MarketBriefingResponse>
}
