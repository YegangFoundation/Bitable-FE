package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.response.ChartAnalysisResponse
import com.example.bitable_fe.core.network.response.OrderBookAnalysisResponse

interface ChartRepository {
    suspend fun getOrderBook(symbol: String): OrderBookAnalysisResponse
    suspend fun getChartAnalysis(symbol: String, interval: String?): ChartAnalysisResponse
}
