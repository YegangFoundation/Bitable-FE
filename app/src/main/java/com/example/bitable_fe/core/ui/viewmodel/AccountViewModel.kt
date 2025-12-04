package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.AccountRepository
import com.example.bitable_fe.core.network.request.CreateAccountRequest
import com.example.bitable_fe.core.ui.state.AccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repo: AccountRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AccountUiState>(AccountUiState.Idle)
    val state = _state.asStateFlow()


    private fun emit(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = AccountUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = AccountUiState.Success(it) }
                .onFailure { _state.value = AccountUiState.Error(it.message ?: "error") }
        }
    }

    fun createAccount(req: CreateAccountRequest) =
        emit { repo.createAccount(req) }

    fun getAccountsByUser(userId: Long) =
        emit { repo.getAccountsByUser(userId) }

    fun getSummary(accountId: Long) =
        emit { repo.getSummary(accountId) }

    fun resetBalance(accountId: Long, newBalance: Double) =
        emit { repo.resetBalance(accountId, newBalance) }

    fun deposit(accountId: Long, amount: Double, memo: String?) =
        emit { repo.deposit(accountId, amount, memo) }

    fun withdraw(accountId: Long, amount: Double, memo: String?) =
        emit { repo.withdraw(accountId, amount, memo) }
}
