package com.example.bitable_fe.core.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.datastore.UserPreferencesDataStore
import com.example.bitable_fe.core.data.model.CoinDetailUi
import com.example.bitable_fe.core.data.model.PieItemUi
import com.example.bitable_fe.core.data.model.PortfolioUi
import com.example.bitable_fe.core.data.repository.iface.HoldingsRepository
import com.example.bitable_fe.core.data.repository.iface.PortfolioRepository
import com.example.bitable_fe.core.network.response.HoldingResponse
import com.example.bitable_fe.core.network.response.PortfolioHistoryResponse
import com.example.bitable_fe.core.network.response.PortfolioSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val repo: PortfolioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PortfolioUi?>(null)
    val uiState = _uiState.asStateFlow()

    private val _holdings = MutableStateFlow<List<HoldingResponse>>(emptyList())
    val holdings = _holdings.asStateFlow()

    private val _historyState = MutableStateFlow<List<PortfolioHistoryResponse>>(emptyList())
    val historyState = _historyState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _chartValues = MutableStateFlow<List<Double>>(emptyList())
    val chartValues = _chartValues.asStateFlow()
    private val _period = MutableStateFlow("일")
    val period = _period.asStateFlow()
    private val _historySummary = MutableStateFlow("")
    val historySummary = _historySummary.asStateFlow()
    fun setPeriod(p: String, accountId: Long) {
        _period.value = p
        val interval = when (p) {
            "일" -> "1d"
            "주" -> "1w"
            "월" -> "1m"
            else -> "1d"
        }
        loadPortfolioHistory(accountId, interval)
    }
    fun loadPortfolioHistory(accountId: Long, interval: String = "1d") {
        viewModelScope.launch {
            _loading.value = true

            val response = repo.getPortfolioHistory(accountId, interval)

            response.data?.let { data ->
                data.history.let { list ->
                    _historyState.value = list
                    _chartValues.value = list.map { it.totalBalanceKrw }
                }

                // 🔥 요약 문장 저장
                _historySummary.value = data.summary ?: ""
            }

            _loading.value = false
        }
    }
    fun loadAll(accountId: Long) {
        loadPortfolio(accountId)
        loadHoldings(accountId)
        loadPortfolioHistory(accountId, _period.value)
    }

    fun loadPortfolio(accountId: Long) {
        viewModelScope.launch {
            runCatching { repo.getSummary(accountId) }
                .onSuccess { summary ->
                    _uiState.value = summary.toUi()
                }
                .onFailure {
                    _uiState.value = null
                }
        }
    }

    fun loadHoldings(accountId: Long) {
        viewModelScope.launch {
            runCatching { repo.getHoldings(accountId) }
                .onSuccess { list ->
                    _holdings.value = list
                }
                .onFailure {
                    _holdings.value = emptyList()
                }
        }
    }

    fun PortfolioSummary.toUi(): PortfolioUi {
        val chartColors = listOf(
            0xFFE57373, 0xFF64B5F6, 0xFF81C784, 0xFFFFB74D, 0xFFBA68C8
        )

        val coins = this.details

        val pieItems = coins.mapIndexed { index, c ->
            PieItemUi(
                name = c.symbol,
                ratio = c.holdingQuantity / coins.sumOf { it.holdingQuantity },
                color = chartColors[index % chartColors.size]
            )
        }

        val coinDetails = coins.map { c ->
            CoinDetailUi(
                symbol = c.symbol,
                name = c.coinName,
                quantity = c.holdingQuantity,
                profit = c.profitLossKrw,
                profitRate = c.profitLossRate,
                evalAmount = c.currentPrice * c.holdingQuantity,
                buyAmount = c.avgBuyPrice * c.holdingQuantity
            )
        }

        return PortfolioUi(
            totalBalance = totalBalanceKrw,
            totalProfit = totalProfitLossKrw,
            totalProfitRate = totalProfitLossRate,
            pieItems = pieItems,
            coinDetails = coinDetails
        )
    }
}

