package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable
@Serializable
data class OrderbookAnalysisResponse(
    val symbol: String,
    val analysis: String,
    val infoLevel: Int
)

@Serializable
data class ChartAnalysisResponse(
    val symbol: String,
    val interval: String,
    val analysis: String,
    val infoLevel: Int
)

@Serializable
data class CandleResponse(
    val market: String? = null,
    val candle_date_time_utc: String? = null,
    val candle_date_time_kst: String? = null,
    val opening_price: Double? = null,
    val high_price: Double? = null,
    val low_price: Double? = null,
    val trade_price: Double? = null,
    val timestamp: Long? = null,
    val candle_acc_trade_price: Double? = null,
    val candle_acc_trade_volume: Double? = null,

    // 🔥 이 부분이 누락되어 있던 필드
    val prev_closing_price: Double? = null,

    val change_price: Double? = null,
    val change_rate: Double? = null
)