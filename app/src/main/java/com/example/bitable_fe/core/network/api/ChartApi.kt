package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.CandleResponse
import com.example.bitable_fe.core.network.response.ChartAnalysisResponse
import com.example.bitable_fe.core.network.response.OrderbookAnalysisResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ChartApi {

    @GET("/api/chart/orderbook")
    suspend fun analyzeOrderBook(
        @Query("userId") userId: Long,
        @Query("symbol") symbol: String
    ): ApiResponse<OrderbookAnalysisResponse>

    @GET("/api/chart/analysis")
    suspend fun analyzeChart(
        @Query("userId") userId: Long,
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "5m"
    ): ApiResponse<ChartAnalysisResponse>

    @GET("/api/chart/candles/minutes/{unit}")
    suspend fun getMinuteCandles(
        @Path("unit") unit: Int,
        @Query("market") market: String,
        @Query("count") count: Int = 200
    ): ApiResponse<List<CandleResponse>>

    @GET("/api/chart/candles/days")
    suspend fun getDayCandles(
        @Query("market") market: String,
        @Query("count") count: Int = 200
    ): ApiResponse<List<CandleResponse>>

    @GET("/api/chart/candles/weeks")
    suspend fun getWeekCandles(
        @Query("market") market: String,
        @Query("count") count: Int = 200
    ): ApiResponse<List<CandleResponse>>
}

