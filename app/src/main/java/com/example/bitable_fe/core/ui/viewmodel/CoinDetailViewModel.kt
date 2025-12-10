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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repo: CoinRepository,
    private val chartRepo: ChartRepository
) : ViewModel() {

    private val _tickerState = MutableStateFlow<CoinDetailState>(CoinDetailState.Loading)
    val tickerState = _tickerState.asStateFlow()

    private val _chartState = MutableStateFlow<List<CandleResponse>>(emptyList())
    val chartState = _chartState.asStateFlow()

    private val _dailyStats = MutableStateFlow<DailyStats?>(null)
    val dailyStats = _dailyStats.asStateFlow()

    private val _chartAnalysis = MutableStateFlow("")
    val chartAnalysis = _chartAnalysis.asStateFlow()

    var period by mutableIntStateOf(0)
        private set

    private var currentSymbol: String = ""

    /** 🔥 실시간 ticker job */
    private var tickerJob: Job? = null


    /** -------------------------
     *  기간 탭 변경
     *  ------------------------- */
    fun setPeriodTab(idx: Int) {
        period = idx
        loadChart(currentSymbol)
    }


    /** -------------------------
     *  TTS 차트 분석 요청
     *  ------------------------- */
    fun loadChartAnalysis(userId: Long = 1L) {
        viewModelScope.launch {
            val interval = when (period) {
                0 -> "1m"
                1 -> "1d"
                2 -> "1w"
                3 -> "1M"
                4 -> "1Y"
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


    /** -------------------------
     *  티커 최초 로드
     *  ------------------------- */
    fun loadTicker(symbol: String) {
        currentSymbol = symbol

        viewModelScope.launch {
            _tickerState.value = CoinDetailState.Loading

            runCatching { repo.getTicker(symbol) }
                .onSuccess { ticker ->
                    _tickerState.value = CoinDetailState.Success(ticker)

                    loadChart(symbol)        // 차트 로드
                    startRealTimeTicker(symbol) // ★ 실시간 티커 시작
                }
                .onFailure {
                    _tickerState.value = CoinDetailState.Error(it.message ?: "Unknown error")
                }
        }
    }
    fun clearChartAnalysis() {
        _chartAnalysis.value = ""
    }

    /** -------------------------
     *  실시간 티커 폴링 시작
     *  ------------------------- */
    private fun startRealTimeTicker(symbol: String) {
        // 기존 job이 돌고 있으면 중지
        tickerJob?.cancel()

        tickerJob = viewModelScope.launch {
            while (isActive) {
                delay(3000)

                runCatching { repo.getTicker(symbol) }
                    .onSuccess { ticker ->
                        _tickerState.value = CoinDetailState.Success(ticker)
                        recalcDailyStats()  // ★ real-time 반영
                    }
            }
        }
    }


    /** -------------------------
     *  실시간 ticker 중단 (화면 벗어날 때)
     *  ------------------------- */
    fun stopRealTimeTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }


    /** -------------------------
     *  차트 로드 (기간 탭별)
     *  ------------------------- */
    private fun loadChart(symbol: String) {
        if (symbol.isBlank()) return

        viewModelScope.launch {
            val resp = when (period) {
                0 -> chartRepo.getMinuteCandles(1, symbol, 200)
                1 -> chartRepo.getDayCandles(symbol, 60)
                2 -> chartRepo.getWeekCandles(symbol, 60)
                3 -> chartRepo.getDayCandles(symbol, 120)
                4 -> chartRepo.getDayCandles(symbol, 365)
                else -> chartRepo.getDayCandles(symbol, 60)
            }

            _chartState.value = resp.data ?: emptyList()
            recalcDailyStats() // ★ 차트 바뀌면 변동률 업데이트
        }
    }


    /** -------------------------
     *  변동률 계산
     *  ------------------------- */
    private fun recalcDailyStats() {
        val candles = _chartState.value
        if (candles.size < 2) return

        val prev = candles[candles.size - 2]   // 어제 종가 등
        val today = candles.last()             // 오늘

        val prevClose = prev.trade_price ?: return
        val trade = today.trade_price ?: return
        val high = today.high_price ?: return
        val low = today.low_price ?: return

        _dailyStats.value = DailyStats(
            changeRate = ((trade - prevClose) / prevClose) * 100,
            highRate = ((high - prevClose) / prevClose) * 100,
            lowRate = ((low - prevClose) / prevClose) * 100
        )
    }


    override fun onCleared() {
        super.onCleared()
        stopRealTimeTicker()
    }
}


data class DailyStats(
    val changeRate: Double,
    val highRate: Double,
    val lowRate: Double
)
