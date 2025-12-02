package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.MarketRepository
import com.example.bitable_fe.core.ui.state.MarketUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val repo: MarketRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MarketUiState>(MarketUiState.Idle)
    val state = _state.asStateFlow()


    fun getMarketBriefing(userId: Long?) {
        viewModelScope.launch {
            _state.value = MarketUiState.Loading
            runCatching { repo.getMarketBriefing(userId) }
                .onSuccess { _state.value = MarketUiState.Success(it) }
                .onFailure { _state.value = MarketUiState.Error(it.message ?: "") }
        }
    }
}
