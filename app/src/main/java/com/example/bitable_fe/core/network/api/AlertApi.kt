package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.CreateAlertRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.PriceAlertResponse
import retrofit2.http.*

interface AlertApi {

    @POST("/api/alerts")
    suspend fun createAlert(
        @Body request: CreateAlertRequest
    ): ApiResponse<PriceAlertResponse>

    @GET("/api/alerts")
    suspend fun getAlerts(
        @Query("accountId") accountId: Long? = null
    ): ApiResponse<List<PriceAlertResponse>>

    @DELETE("/api/alerts/{alertId}")
    suspend fun deactivateAlert(
        @Path("alertId") alertId: Long
    ): ApiResponse<Void?>
}
