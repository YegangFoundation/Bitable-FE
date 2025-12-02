package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.request.BuyRequest
import com.example.bitable_fe.core.network.request.SellRequest
import com.example.bitable_fe.core.network.response.OrderResponse

interface OrderRepository {
    suspend fun buy(req: BuyRequest): OrderResponse
    suspend fun sell(req: SellRequest): OrderResponse
    suspend fun getOrder(orderId: Long): OrderResponse
    suspend fun getOrders(
        accountId: Long?,
        symbol: String?,
        side: String?,
        start: String?,
        end: String?
    ): List<OrderResponse>
}
