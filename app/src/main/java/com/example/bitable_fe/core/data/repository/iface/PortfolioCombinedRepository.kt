package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.data.model.PortfolioUi

interface PortfolioCombinedRepository {
    suspend fun loadPortfolio(accountId: Long): PortfolioUi
}