package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.CoinResponse
import com.example.bitable_fe.core.network.response.MarketData
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {

    @GET("/api/coins")
    suspend fun getAllCoins(): ApiResponse<List<CoinResponse>>

    @GET("/api/coins/{symbol}")
    suspend fun getCoinBySymbol(
        @Path("symbol") symbol: String
    ): ApiResponse<CoinResponse>

    @GET("/api/coins/{symbol}/ticker")
    suspend fun getTicker(
        @Path("symbol") symbol: String
    ): ApiResponse<MarketData>

    @GET("/api/coins/markets")
    suspend fun getAllMarkets(): ApiResponse<List<MarketData>>
}
