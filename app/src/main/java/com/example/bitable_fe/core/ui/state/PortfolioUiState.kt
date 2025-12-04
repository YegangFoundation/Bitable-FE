package com.example.bitable_fe.core.ui.state

import com.example.bitable_fe.core.network.response.PortfolioSummary

sealed class PortfolioUiState {
    object Idle : PortfolioUiState()
    object Loading : PortfolioUiState()
    data class Success(val data: PortfolioSummary) : PortfolioUiState()
    data class Error(val msg: String) : PortfolioUiState()
}