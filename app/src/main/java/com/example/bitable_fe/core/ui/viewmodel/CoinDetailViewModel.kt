package com.example.bitable_fe.core.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.ChartRepository
import com.example.bitable_fe.core.data.repository.iface.CoinRepository
import com.example.bitable_fe.core.network.response.CandleResponse
import com.example.bitable_fe.core.ui.state.CoinDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repo: CoinRepository,
    private val chartRepo: ChartRepository
) : ViewModel() {

    var period by mutableIntStateOf(0)
        private set

    private val _tickerState = MutableStateFlow<CoinDetailState>(CoinDetailState.Loading)
    val tickerState = _tickerState.asStateFlow()

    private val _chartState = MutableStateFlow<List<CandleResponse>>(emptyList())
    val chartState = _chartState.asStateFlow()

    private var currentSymbol: String = ""
    private val _chartAnalysis = MutableStateFlow<String>("")
    val chartAnalysis = _chartAnalysis.asStateFlow()

    fun setPeriodTab(idx: Int) {
        period = idx
        loadChart(currentSymbol)
    }
    fun loadChartAnalysis(userId: Long = 1L) {
        viewModelScope.launch {
            val interval = when (period) {
                0 -> "1d"
                1 -> "1w"
                2 -> "1m"
                3 -> "5m"
                4 -> "30m"
                5 -> "1h"
                6 -> "4h"
                else -> "1d"
            }

            val resp = chartRepo.analyzeChart(
                userId = userId,
                symbol = currentSymbol,
                interval = interval
            )

            resp.data?.analysis?.let {
                _chartAnalysis.value = it
            }
        }
    }
    fun loadTicker(symbol: String) {
        currentSymbol = symbol

        viewModelScope.launch {
            _tickerState.value = CoinDetailState.Loading

            runCatching { repo.getTicker(symbol) }
                .onSuccess { data ->
                    _tickerState.value = CoinDetailState.Success(data)
                    loadChart(symbol)   // 🎯 티커 로드 후 차트도 함께 로드!
                }
                .onFailure {
                    _tickerState.value = CoinDetailState.Error(it.message ?: "Unknown error")
                }
        }
    }

    // ---------------------------------------------------------
    // 🔥 차트 로딩
    // ---------------------------------------------------------
    private fun loadChart(symbol: String) {
        if (symbol.isBlank()) return

        viewModelScope.launch {
            val market = symbol // 예: "KRW-BTC"

            val response = when (period) {
                0 -> chartRepo.getDayCandles(market, 60)      // 1일
                1 -> chartRepo.getWeekCandles(market, 60)     // 1주
                2 -> chartRepo.getDayCandles(market, 120)     // 1개월 (일봉 120개)
                3 -> chartRepo.getMinuteCandles(5, market, 200)   // 5분봉
                4 -> chartRepo.getMinuteCandles(30, market, 200)  // 30분봉
                5 -> chartRepo.getMinuteCandles(60, market, 200)  // 1시간봉
                6 -> chartRepo.getMinuteCandles(240, market, 200) // 4시간봉
                else -> chartRepo.getDayCandles(market, 60)
            }

            _chartState.value = response.data ?: emptyList()
        }
    }
}
