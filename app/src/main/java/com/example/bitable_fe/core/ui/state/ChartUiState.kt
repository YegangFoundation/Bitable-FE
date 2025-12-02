package com.example.bitable_fe.core.ui.state

sealed class ChartUiState {
    object Idle : ChartUiState()
    object Loading : ChartUiState()
    data class Success(val data: Any) : ChartUiState()
    data class Error(val msg: String) : ChartUiState()
}