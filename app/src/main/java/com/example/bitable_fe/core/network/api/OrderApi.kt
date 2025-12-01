package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.BuyRequest
import com.example.bitable_fe.core.network.request.SellRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.OrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    suspend fun getOrdersByAccount(
        @Query("accountId") accountId: Long,
        @Query("symbol") symbol: String? = null,
        @Query("side") side: String? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): ApiResponse<List<OrderResponse>>
}
