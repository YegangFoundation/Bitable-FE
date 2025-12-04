package com.example.bitable_fe.core.ui.state

sealed class BankUiState {
    object Idle : BankUiState()
    object Loading : BankUiState()
    data class Success(val data: Any) : BankUiState()
    data class Error(val msg: String) : BankUiState()
}
