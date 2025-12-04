package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.OrderRepository
import com.example.bitable_fe.core.network.api.OrderApi
import com.example.bitable_fe.core.network.request.BuyRequest
import com.example.bitable_fe.core.network.request.SellRequest
import com.example.bitable_fe.core.network.response.OrderResponse
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi
) : OrderRepository {

    override suspend fun buy(req: BuyRequest): OrderResponse =
        api.buy(req).data!!

    override suspend fun sell(req: SellRequest): OrderResponse =
        api.sell(req).data!!

    override suspend fun getOrder(orderId: Long): OrderResponse =
        api.getOrder(orderId).data!!

    override suspend fun getOrders(accountId: Long?, symbol: String?, side: String?, start: String?, end: String?) =
        api.getOrders(accountId, symbol, side, start, end).data!!
}
