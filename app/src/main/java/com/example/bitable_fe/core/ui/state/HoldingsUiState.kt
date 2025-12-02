package com.example.bitable_fe.core.ui.state

sealed class HoldingsUiState {
    object Idle : HoldingsUiState()
    object Loading : HoldingsUiState()
    data class Success(val data: Any) : HoldingsUiState()
    data class Error(val msg: String) : HoldingsUiState()
}
