package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.TransactionRepository
import com.example.bitable_fe.core.ui.state.TransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repo: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow<TransactionUiState>(TransactionUiState.Idle)
    val state = _state.asStateFlow()


    fun getDeposits(accountId: Long) =
        fetch { repo.getDeposits(accountId) }

    fun getWithdrawals(accountId: Long) =
        fetch { repo.getWithdrawals(accountId) }

    private fun fetch(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = TransactionUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = TransactionUiState.Success(it) }
                .onFailure { _state.value = TransactionUiState.Error(it.message ?: "") }
        }
    }
}
