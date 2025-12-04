package com.example.bitable_fe.core.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.CoinRepository
import com.example.bitable_fe.core.ui.state.CoinDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repo: CoinRepository
) : ViewModel() {

    var period by mutableIntStateOf(1)
        private set

    fun setPeriodTab(idx: Int) {
        period = idx
    }

    private val _tickerState = MutableStateFlow<CoinDetailState>(CoinDetailState.Loading)
    val tickerState = _tickerState.asStateFlow()

    fun loadTicker(symbol: String) {
        viewModelScope.launch {
            _tickerState.value = CoinDetailState.Loading

            runCatching { repo.getTicker(symbol) }
                .onSuccess { data ->
                    _tickerState.value = CoinDetailState.Success(data)
                }
                .onFailure {
                    _tickerState.value = CoinDetailState.Error(it.message ?: "Unknown error")
                }
        }
    }
}
