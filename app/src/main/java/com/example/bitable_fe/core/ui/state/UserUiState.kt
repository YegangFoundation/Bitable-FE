package com.example.bitable_fe.core.ui.state

sealed class UserUiState {
    object Idle : UserUiState()
    object Loading : UserUiState()
    data class Success(val data: Any) : UserUiState()
    data class Error(val msg: String) : UserUiState()
}