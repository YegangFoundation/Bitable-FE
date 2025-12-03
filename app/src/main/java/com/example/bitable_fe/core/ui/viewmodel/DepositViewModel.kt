package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.AccountRepository
import com.example.bitable_fe.core.data.repository.iface.HoldingsRepository
import com.example.bitable_fe.core.data.repository.iface.PortfolioRepository
import com.example.bitable_fe.core.data.repository.iface.TransactionRepository
import com.example.bitable_fe.core.network.response.AccountInfo
import com.example.bitable_fe.core.network.response.HoldingResponse
import com.example.bitable_fe.core.network.response.TransactionResponse
import com.example.bitable_fe.core.ui.state.AccountUiState
import com.example.bitable_fe.core.ui.state.HoldingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository,
    private val holdingRepo: HoldingsRepository
) : ViewModel() {

    // ------------------------------
    // KRW Summary
    // ------------------------------
    private val _summary = MutableStateFlow<AccountUiState>(AccountUiState.Idle)
    val summary = _summary.asStateFlow()

    // ------------------------------
    // 보유 코인 리스트
    // ------------------------------
    private val _holdings = MutableStateFlow<HoldingsUiState<List<HoldingResponse>>>(HoldingsUiState.Idle)
    val holdings = _holdings.asStateFlow()
    // ------------------------------
    // 입금 & 출금 로그
    // ------------------------------
    private val _depositLog = MutableStateFlow<List<TransactionResponse>>(emptyList())
    val depositLog: StateFlow<List<TransactionResponse>> = _depositLog.asStateFlow()

    private val _withdrawLog = MutableStateFlow<List<TransactionResponse>>(emptyList())
    val withdrawLog: StateFlow<List<TransactionResponse>> = _withdrawLog.asStateFlow()

    // ------------------------------
    // 계좌 정보
    // ------------------------------
    private val _account = MutableStateFlow<AccountInfo?>(null)
    val account: StateFlow<AccountInfo?> = _account.asStateFlow()

    // ------------------------------
    // 금액 입력 숫자패드
    // ------------------------------
    private val _amount = MutableStateFlow("0")
    val amount: StateFlow<String> = _amount.asStateFlow()

    // ------------------------------
    // 현재 선택된 계좌 (accountId)
    // 실제로는 UserPreferences에서 불러오는 경우가 많음
    // ------------------------------
    var selectedAccountId: Long = 1L

    // ============================================================
    // 초기 로드
    // ============================================================
    fun loadAll(accountId: Long = selectedAccountId) {
        selectedAccountId = accountId
        fetchSummary()
        fetchHoldings()
        fetchDepositLogs()
        fetchWithdrawLogs()
    }

    fun fetchSummary() {
        viewModelScope.launch {
            runCatching {
                accountRepo.getSummary(selectedAccountId)
            }.onSuccess {
                _summary.value = AccountUiState.Success(it)
                _account.value = it
            }
        }
    }

    fun fetchHoldings() {
        viewModelScope.launch {
            _holdings.value = HoldingsUiState.Loading
            runCatching {
                holdingRepo.getHoldings(selectedAccountId)
            }.onSuccess { list ->
                _holdings.value = HoldingsUiState.Success(list)
            }.onFailure { exception ->
                _holdings.value = HoldingsUiState.Error(exception.message ?: "")
            }
        }
    }

    fun fetchDepositLogs() {
        viewModelScope.launch {
            runCatching {
                transactionRepo.getDeposits(selectedAccountId)
            }.onSuccess {
                _depositLog.value = it
            }
        }
    }

    fun fetchWithdrawLogs() {
        viewModelScope.launch {
            runCatching {
                transactionRepo.getWithdrawals(selectedAccountId)
            }.onSuccess {
                _withdrawLog.value = it
            }
        }
    }

    // ============================================================
    // 숫자패드 입력
    // ============================================================
    fun onKeyClicked(key: String) {
        var current = _amount.value

        when (key) {
            "⌫" -> {
                current = if (current.length > 1) current.dropLast(1) else "0"
            }

            "0", "00", "1","2","3","4","5","6","7","8","9" -> {
                current = if (current == "0") key else current + key
            }
        }

        // 금액 오버플로 방지 + 선행 0 제거
        _amount.value = current.trimStart('0').ifEmpty { "0" }
    }

    fun clearAmount() {
        _amount.value = "0"
    }

    // ============================================================
    // 입금 요청
    // ============================================================
    fun requestDeposit(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val money = _amount.value.toLongOrNull() ?: 0L
            if (money <= 0) return@launch

            runCatching {
                accountRepo.deposit(selectedAccountId, money.toDouble())
            }.onSuccess {
                clearAmount()
                fetchSummary()
                fetchDepositLogs()
                onSuccess()
            }
        }
    }

    // ============================================================
    // 출금 요청
    // ============================================================
    fun requestWithdraw(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val money = _amount.value.toLongOrNull() ?: 0L
            if (money <= 0) return@launch

            runCatching {
                accountRepo.withdraw(selectedAccountId, money.toDouble())
            }.onSuccess {
                clearAmount()
                fetchSummary()
                fetchWithdrawLogs()
                onSuccess()
            }
        }
    }
}
