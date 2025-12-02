package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.PortfolioRepository
import com.example.bitable_fe.core.ui.state.PortfolioUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val repo: PortfolioRepository
) : ViewModel() {

    private val _state = MutableStateFlow<PortfolioUiState>(PortfolioUiState.Idle)
    val state = _state.asStateFlow()

    fun getSummary(accountId: Long) {
        viewModelScope.launch {
            _state.value = PortfolioUiState.Loading
            runCatching { repo.getSummary(accountId) }
                .onSuccess { _state.value = PortfolioUiState.Success(it) }
                .onFailure { _state.value = PortfolioUiState.Error(it.message ?: "") }
        }
    }
}
