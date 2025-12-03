package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.AccountRepository
import com.example.bitable_fe.core.data.repository.iface.HoldingsRepository
import com.example.bitable_fe.core.data.repository.iface.TransactionRepository
import com.example.bitable_fe.core.network.response.AccountInfo
import com.example.bitable_fe.core.network.response.HoldingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfitViewModel @Inject constructor(
    private val accountRepo: AccountRepository,
    private val holdingsRepo: HoldingsRepository,
    private val transactionRepo: TransactionRepository
) : ViewModel() {

    /** 기간 설정: 일, 주, 월 */
    private val _period = MutableStateFlow("일")
    val period = _period.asStateFlow()

    fun setPeriod(p: String) { _period.value = p }

    /** 총계 정보 */
    private val _summary = MutableStateFlow<AccountInfo?>(null)
    val summary = _summary.asStateFlow()

    /** 평가손익 / 보유 자산 */
    private val _holdings = MutableStateFlow<List<HoldingResponse>>(emptyList())
    val holdings = _holdings.asStateFlow()

    /** 기간 평균 투자금액 */
    private val _avgInvest = MutableStateFlow(0.0)
    val avgInvest = _avgInvest.asStateFlow()

    /** 그래프 데이터 (간단 더미) */
    private val _chartData = MutableStateFlow<List<Double>>(emptyList())
    val chartData = _chartData.asStateFlow()

    var accountId: Long = 1L

    /** 초기 로드 */
    fun loadAll(id: Long = accountId) {
        accountId = id
        fetchSummary()
        fetchHoldings()
        fetchAverageInvestment()
        generateDummyChart()
    }

    private fun fetchSummary() {
        viewModelScope.launch {
            runCatching { accountRepo.getSummary(accountId) }
                .onSuccess { _summary.value = it }
        }
    }

    private fun fetchHoldings() {
        viewModelScope.launch {
            runCatching { holdingsRepo.getHoldings(accountId) }
                .onSuccess { _holdings.value = it }
        }
    }

    /** 네 transactionRepo 구조에 맞춰서 수정 가능 */
    private fun fetchAverageInvestment() {
        viewModelScope.launch {
            runCatching { transactionRepo.getDeposits(accountId) }
                .onSuccess { list ->
                    if (list.isNotEmpty()) {
                        _avgInvest.value =
                            list.map { it.amount }.average()
                    }
                }
        }
    }

    /** 일/주/월에 따라 차트 변경 가능 → 일단 더미 */
    fun generateDummyChart() {
        _chartData.value = List(20) { (20..200).random().toDouble() }
    }
}
