package com.example.bitable_fe.core.ui.state

import com.example.bitable_fe.core.network.response.MarketData

sealed class CoinDetailState {
    object Loading : CoinDetailState()
    data class Success(val data: MarketData) : CoinDetailState()
    data class Error(val message: String) : CoinDetailState()
}