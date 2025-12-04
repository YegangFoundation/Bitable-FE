package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.response.PortfolioSummary

interface PortfolioRepository {
    suspend fun getSummary(accountId: Long): PortfolioSummary
}
