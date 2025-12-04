package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.response.MarketBriefingResponse

interface MarketRepository {
    suspend fun getMarketBriefing(userId: Long?): MarketBriefingResponse
}
