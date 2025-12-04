package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.ChartRepository
import com.example.bitable_fe.core.ui.state.ChartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val repo: ChartRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ChartUiState>(ChartUiState.Idle)
    val state = _state.asStateFlow()

    fun getOrderBook(symbol: String) =
        emit { repo.getOrderBook(symbol) }

    fun getChartAnalysis(symbol: String, interval: String?) =
        emit { repo.getChartAnalysis(symbol, interval) }

    private fun emit(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = ChartUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = ChartUiState.Success(it) }
                .onFailure { _state.value = ChartUiState.Error(it.message ?: "") }
        }
    }
}
