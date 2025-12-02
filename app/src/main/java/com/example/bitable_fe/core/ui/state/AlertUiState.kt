package com.example.bitable_fe.core.ui.state

sealed class AlertUiState {
    object Idle : AlertUiState()
    object Loading : AlertUiState()
    data class Success(val data: Any) : AlertUiState()
    data class Error(val msg: String) : AlertUiState()
}
