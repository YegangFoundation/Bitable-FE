package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.HoldingsRepository
import com.example.bitable_fe.core.ui.state.HoldingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repo: HoldingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HoldingsUiState>(HoldingsUiState.Idle)
    val state = _state.asStateFlow()


    fun getHoldings(accountId: Long) {
        viewModelScope.launch {
            _state.value = HoldingsUiState.Loading
            runCatching { repo.getHoldings(accountId) }
                .onSuccess { _state.value = HoldingsUiState.Success(it) }
                .onFailure { _state.value = HoldingsUiState.Error(it.message ?: "error") }
        }
    }
}
