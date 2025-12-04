package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.PortfolioRepository
import com.example.bitable_fe.core.network.api.PortfolioApi
import com.example.bitable_fe.core.network.response.PortfolioSummary
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val api: PortfolioApi
) : PortfolioRepository {

    override suspend fun getSummary(accountId: Long): PortfolioSummary =
        api.getPortfolioSummary(accountId).data!!
}
