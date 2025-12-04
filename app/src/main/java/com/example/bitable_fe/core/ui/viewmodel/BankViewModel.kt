package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.BankRepository
import com.example.bitable_fe.core.network.request.LinkBankAccountRequest
import com.example.bitable_fe.core.ui.state.BankUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor(
    private val repo: BankRepository
) : ViewModel() {

    private val _state = MutableStateFlow<BankUiState>(BankUiState.Idle)
    val state = _state.asStateFlow()


    private fun emit(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = BankUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = BankUiState.Success(it) }
                .onFailure { _state.value = BankUiState.Error(it.message ?: "error") }
        }
    }

    fun getAvailableAccounts() =
        emit { repo.getAvailableAccounts() }

    fun linkAccount(req: LinkBankAccountRequest) =
        emit { repo.linkBankAccount(req) }
}
