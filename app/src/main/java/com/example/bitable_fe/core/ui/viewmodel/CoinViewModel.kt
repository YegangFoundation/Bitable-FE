package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.CoinRepository
import com.example.bitable_fe.core.network.request.RegisterCoinRequest
import com.example.bitable_fe.core.network.response.MarketData
import com.example.bitable_fe.core.ui.state.CoinUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

// CoinViewModel.kt
@HiltViewModel
class CoinViewModel @Inject constructor(
    private val repo: CoinRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CoinUiState<Any>>(CoinUiState.Idle)
    val state = _state.asStateFlow()

    private val _tickers = MutableStateFlow<Map<String, MarketData>>(emptyMap())
    val tickers = _tickers.asStateFlow()

    private var tickerJob: Job? = null


    private fun <T> emit(block: suspend () -> T) {
        viewModelScope.launch {
            _state.value = CoinUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = CoinUiState.Success(it as Any) }
                .onFailure { _state.value = CoinUiState.Error(it.message.orEmpty()) }
        }
    }


    fun getAllMarkets() =
        emit { repo.getAllMarkets() }


    suspend fun loadTicker(symbol: String) {
        runCatching { repo.getTicker(symbol) }
            .onSuccess { ticker ->
                _tickers.value = _tickers.value + (symbol to ticker)
            }
    }


    // 🔥 실시간 ticker 시작
    fun startRealTimeTicker(symbols: List<String>) {
        // 중복 실행 방지
        tickerJob?.cancel()

        tickerJob = viewModelScope.launch {
            while (isActive) {
                symbols.forEach { symbol ->
                    loadTicker(symbol)
                }
                delay(2000) // 2초마다 전체 refresh
            }
        }
    }

    // 🔥 실시간 ticker 종료
    fun stopRealTimeTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }

    override fun onCleared() {
        super.onCleared()
        tickerJob?.cancel()
    }
}
