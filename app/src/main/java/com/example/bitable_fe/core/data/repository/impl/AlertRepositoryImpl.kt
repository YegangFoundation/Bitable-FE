package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.AlertRepository
import com.example.bitable_fe.core.network.api.AlertApi
import com.example.bitable_fe.core.network.request.CreateAlertRequest
import com.example.bitable_fe.core.network.response.PriceAlertResponse
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(
    private val api: AlertApi
) : AlertRepository {

    override suspend fun createAlert(req: CreateAlertRequest): PriceAlertResponse =
        api.createAlert(req).data!!

    override suspend fun getAlerts(accountId: Long?): List<PriceAlertResponse> =
        api.getAlerts(accountId).data!!

    override suspend fun deleteAlert(alertId: Long) {
        api.deactivateAlert(alertId)
    }
}
