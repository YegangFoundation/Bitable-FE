package com.example.bitable_fe.core.ui.state

sealed class AccountUiState {
    object Idle : AccountUiState()
    object Loading : AccountUiState()
    data class Success(val data: Any) : AccountUiState()
    data class Error(val msg: String) : AccountUiState()
}