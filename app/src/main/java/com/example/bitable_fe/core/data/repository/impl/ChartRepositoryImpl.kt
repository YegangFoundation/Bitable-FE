package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.ChartRepository
import com.example.bitable_fe.core.network.api.ChartApi
import com.example.bitable_fe.core.network.response.ChartAnalysisResponse
import com.example.bitable_fe.core.network.response.OrderBookAnalysisResponse
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val api: ChartApi
) : ChartRepository {

    override suspend fun getOrderBook(symbol: String): OrderBookAnalysisResponse =
        api.getOrderBookAnalysis(symbol).data!!

    override suspend fun getChartAnalysis(symbol: String, interval: String?): ChartAnalysisResponse =
        api.getChartAnalysis(symbol, interval).data!!
}
