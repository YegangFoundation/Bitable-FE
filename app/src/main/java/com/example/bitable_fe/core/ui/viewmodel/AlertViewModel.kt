package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.AlertRepository
import com.example.bitable_fe.core.network.request.CreateAlertRequest
import com.example.bitable_fe.core.ui.state.AlertUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val repo: AlertRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AlertUiState>(AlertUiState.Idle)
    val state = _state.asStateFlow()

    fun createAlert(req: CreateAlertRequest) =
        emit { repo.createAlert(req) }

    fun getAlerts(accountId: Long?) =
        emit { repo.getAlerts(accountId) }

    fun deleteAlert(alertId: Long) =
        emit { repo.deleteAlert(alertId) }

    private fun emit(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = AlertUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = AlertUiState.Success(it) }
                .onFailure { _state.value = AlertUiState.Error(it.message ?: "") }
        }
    }
}
