package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.request.RegisterCoinRequest
import com.example.bitable_fe.core.network.response.CoinResponse
import com.example.bitable_fe.core.network.response.MarketData

interface CoinRepository {
    suspend fun registerCoin(req: RegisterCoinRequest): CoinResponse
    suspend fun getAllCoins(): List<CoinResponse>
    suspend fun getCoin(symbol: String): CoinResponse
    suspend fun getTicker(symbol: String): MarketData
    suspend fun getAllMarkets(): List<MarketData>
    suspend fun initialize(): Unit
}
