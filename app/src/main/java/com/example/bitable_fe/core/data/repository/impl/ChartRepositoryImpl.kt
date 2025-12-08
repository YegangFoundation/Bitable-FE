package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.ChartRepository
import com.example.bitable_fe.core.network.api.ChartApi
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.CandleResponse
import com.example.bitable_fe.core.network.response.ChartAnalysisResponse
import com.example.bitable_fe.core.network.response.OrderbookAnalysisResponse
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val api: ChartApi
) : ChartRepository {
    override suspend fun analyzeOrderBook(userId: Long, symbol: String): ApiResponse<OrderbookAnalysisResponse> =
        api.analyzeOrderBook(userId, symbol)

    override suspend fun analyzeChart(userId: Long, symbol: String, interval: String): ApiResponse<ChartAnalysisResponse> =
        api.analyzeChart(userId, symbol, interval)

    override suspend fun getMinuteCandles(unit: Int, market: String, count: Int): ApiResponse<List<CandleResponse>> =
        api.getMinuteCandles(unit, market, count)

    override suspend fun getDayCandles(market: String, count: Int): ApiResponse<List<CandleResponse>> =
        api.getDayCandles(market, count)

    override suspend fun getWeekCandles(market: String, count: Int): ApiResponse<List<CandleResponse>> =
        api.getWeekCandles(market, count)
}
