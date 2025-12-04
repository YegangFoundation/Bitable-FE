package com.example.bitable_fe.core.ui.state


sealed interface CoinUiState<out T> {
    object Idle : CoinUiState<Nothing>
    object Loading : CoinUiState<Nothing>
    data class Success<T>(val data: T) : CoinUiState<T>
    data class Error(val msg: String) : CoinUiState<Nothing>
}