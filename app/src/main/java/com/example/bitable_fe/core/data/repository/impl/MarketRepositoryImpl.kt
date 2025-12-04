package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.MarketRepository
import com.example.bitable_fe.core.network.api.MarketApi
import com.example.bitable_fe.core.network.response.MarketBriefingResponse
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val api: MarketApi
) : MarketRepository {

    override suspend fun getMarketBriefing(userId: Long?): MarketBriefingResponse =
        api.getMarketBriefing(userId).data!!
}
