package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.CoinRepository
import com.example.bitable_fe.core.network.api.CoinApi
import com.example.bitable_fe.core.network.request.RegisterCoinRequest
import com.example.bitable_fe.core.network.response.CoinResponse
import com.example.bitable_fe.core.network.response.MarketData
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi
) : CoinRepository {

    override suspend fun registerCoin(req: RegisterCoinRequest): CoinResponse =
        api.registerCoin(req).data!!

    override suspend fun getAllCoins(): List<CoinResponse> =
        api.getAllCoins().data!!

    override suspend fun getCoin(symbol: String): CoinResponse =
        api.getCoinBySymbol(symbol).data!!

    override suspend fun getTicker(symbol: String): MarketData =
        api.getTicker(symbol).data!!

    override suspend fun getAllMarkets(): List<MarketData> =
        api.getAllMarkets().data!!

    override suspend fun initialize() {
        api.initializeCoins()
    }
}
