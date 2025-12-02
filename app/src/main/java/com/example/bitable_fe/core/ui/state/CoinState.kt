package com.example.bitable_fe.core.ui.state

sealed class CoinUiState {
    object Idle : CoinUiState()
    object Loading : CoinUiState()
    data class Success(val data: Any) : CoinUiState()
    data class Error(val msg: String) : CoinUiState()
}