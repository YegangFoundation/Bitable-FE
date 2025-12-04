package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.request.CreateAlertRequest
import com.example.bitable_fe.core.network.response.PriceAlertResponse

interface AlertRepository {
    suspend fun createAlert(req: CreateAlertRequest): PriceAlertResponse
    suspend fun getAlerts(accountId: Long?): List<PriceAlertResponse>
    suspend fun deleteAlert(alertId: Long)
}
