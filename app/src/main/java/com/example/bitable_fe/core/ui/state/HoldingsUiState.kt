package com.example.bitable_fe.core.ui.state

sealed class HoldingsUiState<out T> {
    object Idle : HoldingsUiState<Nothing>()
    object Loading : HoldingsUiState<Nothing>()
    data class Success<T>(val data: T) : HoldingsUiState<T>()
    data class Error(val msg: String) : HoldingsUiState<Nothing>()
}
