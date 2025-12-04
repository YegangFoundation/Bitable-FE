package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.HoldingsRepository
import com.example.bitable_fe.core.network.api.PortfolioApi
import com.example.bitable_fe.core.network.response.HoldingResponse
import javax.inject.Inject

class HoldingsRepositoryImpl @Inject constructor(
    private val api: PortfolioApi
) : HoldingsRepository {

    override suspend fun getHoldings(accountId: Long): List<HoldingResponse> =
        api.getHoldingsByAccount(accountId).data!!
}
