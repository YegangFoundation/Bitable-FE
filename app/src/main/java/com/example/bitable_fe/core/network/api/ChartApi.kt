package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.ChartAnalysisResponse
import com.example.bitable_fe.core.network.response.OrderBookAnalysisResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ChartApi {

    @GET("/api/chart/orderbook")
    suspend fun getOrderBookAnalysis(
        @Query("symbol") symbol: String
    ): ApiResponse<OrderBookAnalysisResponse>

    @GET("/api/chart/analysis")
    suspend fun getChartAnalysis(
        @Query("symbol") symbol: String,
        @Query("interval") interval: String? = null
    ): ApiResponse<ChartAnalysisResponse>
}
