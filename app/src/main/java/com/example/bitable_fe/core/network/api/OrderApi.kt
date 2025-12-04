package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.BuyRequest
import com.example.bitable_fe.core.network.request.SellRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.OrderResponse
import retrofit2.http.*

interface OrderApi {

    @POST("/api/orders/buy")
    suspend fun buy(
        @Body request: BuyRequest
    ): ApiResponse<OrderResponse>

    @POST("/api/orders/sell")
    suspend fun sell(
        @Body request: SellRequest
    ): ApiResponse<OrderResponse>

    @GET("/api/orders")
    suspend fun getOrders(
        @Query("accountId") accountId: Long? = null,
        @Query("symbol") symbol: String? = null,
        @Query("side") side: String? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): ApiResponse<List<OrderResponse>>

    @GET("/api/orders/{orderId}")
    suspend fun getOrder(
        @Path("orderId") orderId: Long
    ): ApiResponse<OrderResponse>
}
