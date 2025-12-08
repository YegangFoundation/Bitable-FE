package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.CandleResponse
import com.example.bitable_fe.core.network.response.ChartAnalysisResponse
import com.example.bitable_fe.core.network.response.OrderbookAnalysisResponse

interface ChartRepository {
    suspend fun analyzeOrderBook(userId: Long, symbol: String): ApiResponse<OrderbookAnalysisResponse>
    suspend fun analyzeChart(userId: Long, symbol: String, interval: String = "5m"): ApiResponse<ChartAnalysisResponse>
    suspend fun getMinuteCandles(unit: Int, market: String, count:Int = 200): ApiResponse<List<CandleResponse>>
    suspend fun getDayCandles(market: String, count: Int = 200): ApiResponse<List<CandleResponse>>
    suspend fun getWeekCandles(market: String, count: Int = 200): ApiResponse<List<CandleResponse>>
}
