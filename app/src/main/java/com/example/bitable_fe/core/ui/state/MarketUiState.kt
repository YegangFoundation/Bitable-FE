package com.example.bitable_fe.core.ui.state

import com.example.bitable_fe.core.network.response.MarketBriefingResponse

sealed class MarketUiState {
    object Idle : MarketUiState()
    object Loading : MarketUiState()
    data class Success(val data: MarketBriefingResponse) : MarketUiState()
    data class Error(val msg: String) : MarketUiState()
}
