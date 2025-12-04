package com.example.bitable_fe.core.ui.state

sealed class TransactionUiState {
    object Idle : TransactionUiState()
    object Loading : TransactionUiState()
    data class Success(val data: Any) : TransactionUiState()
    data class Error(val msg: String) : TransactionUiState()
}