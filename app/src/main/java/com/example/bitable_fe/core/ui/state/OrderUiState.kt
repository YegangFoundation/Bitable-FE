package com.example.bitable_fe.core.ui.state

sealed class OrderUiState {
    object Idle : OrderUiState()
    object Loading : OrderUiState()
    data class Success(val data: Any) : OrderUiState()
    data class Error(val msg: String) : OrderUiState()
}