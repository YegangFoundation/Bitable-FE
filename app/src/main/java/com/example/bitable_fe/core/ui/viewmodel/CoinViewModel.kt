package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.CoinRepository
import com.example.bitable_fe.core.network.request.RegisterCoinRequest
import com.example.bitable_fe.core.ui.state.CoinUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val repo: CoinRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CoinUiState<Any>>(CoinUiState.Idle)
    val state = _state.asStateFlow()

    private fun <T> emit(block: suspend () -> T) {
        viewModelScope.launch {
            _state.value = CoinUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = CoinUiState.Success(it as Any) }
                .onFailure { _state.value = CoinUiState.Error(it.message.orEmpty()) }
        }
    }

    fun registerCoin(req: RegisterCoinRequest) =
        emit { repo.registerCoin(req) }

    fun getAllCoins() =
        emit { repo.getAllCoins() }

    fun getCoin(symbol: String) =
        emit { repo.getCoin(symbol) }

    fun getTicker(symbol: String) =
        emit { repo.getTicker(symbol) }

    fun getAllMarkets() =
        emit { repo.getAllMarkets() }

    fun initialize() =
        emit { repo.initialize() }
}
