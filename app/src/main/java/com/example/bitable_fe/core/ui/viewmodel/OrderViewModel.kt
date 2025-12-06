package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.OrderRepository
import com.example.bitable_fe.core.network.request.BuyRequest
import com.example.bitable_fe.core.network.request.SellRequest
import com.example.bitable_fe.core.ui.state.OrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repo: OrderRepository
) : ViewModel() {

    private val _state = MutableStateFlow<OrderUiState>(OrderUiState.Idle)
    val state = _state.asStateFlow()

    private fun emit(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = OrderUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = OrderUiState.Success(it) }
                .onFailure { _state.value = OrderUiState.Error(it.message ?: "Error") }
        }
    }

    /** 🔥 매수 요청 */
    fun buy(accountId: Long, symbol: String, amountKrw: Double) =
        emit { repo.buy(BuyRequest(accountId, symbol, amountKrw)) }

    /** 🔥 매도 요청 */
    fun sell(accountId: Long, symbol: String, quantity: Double) =
        emit { repo.sell(SellRequest(accountId, symbol, quantity)) }
}
