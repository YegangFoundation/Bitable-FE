package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.response.HoldingResponse

interface HoldingsRepository {
    suspend fun getHoldings(accountId: Long): List<HoldingResponse>
}
