package com.example.bitable_fe.core.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.datastore.UserPreferencesDataStore
import com.example.bitable_fe.core.data.repository.iface.HoldingsRepository
import com.example.bitable_fe.core.data.repository.iface.PortfolioRepository
import com.example.bitable_fe.core.network.response.HoldingResponse
import com.example.bitable_fe.core.network.response.PortfolioSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val portfolioRepo: PortfolioRepository,
    private val holdingsRepo: HoldingsRepository,
    private val prefs: UserPreferencesDataStore
) : ViewModel() {

    // --- UI State ---
    var summary by mutableStateOf<PortfolioSummary?>(null)
        private set

    var holdings by mutableStateOf<List<HoldingResponse>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set


    init {
        loadPortfolio()
    }

    fun loadPortfolio() {
        viewModelScope.launch {
            loading = true
            error = null

            // ⭐ userId 또는 accountId 가져오기
            val accountId = prefs.userId.first()

            runCatching {
                // 병렬로 API 요청
                val summaryDeferred = async { portfolioRepo.getSummary(accountId) }
                val holdingsDeferred = async { holdingsRepo.getHoldings(accountId) }

                summary = summaryDeferred.await()
                holdings = holdingsDeferred.await()
            }
                .onFailure {
                    error = it.message ?: "Unknown Error"
                }

            loading = false
        }
    }
}
