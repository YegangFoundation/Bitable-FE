package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.UserRepository
import com.example.bitable_fe.core.network.request.UpdateSettingsRequest
import com.example.bitable_fe.core.ui.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UserUiState>(UserUiState.Idle)
    val state = _state.asStateFlow()


    private fun emit(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = UserUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = UserUiState.Success(it) }
                .onFailure { _state.value = UserUiState.Error(it.message ?: "error") }
        }
    }

    fun createUser(phone: String, name: String) =
        emit { repo.createUser(phone, name) }

    fun getUser(userId: Long) =
        emit { repo.getUser(userId) }

    fun getUserByPhone(phone: String) =
        emit { repo.getUserByPhone(phone) }

    fun completeOnboarding(userId: Long) =
        emit { repo.completeOnboarding(userId) }

    fun setDefaultBankAccount(userId: Long, bankAccountId: Long) =
        emit { repo.setDefaultBankAccount(userId, bankAccountId) }

    fun getUserBankAccounts(userId: Long) =
        emit { repo.getBankAccounts(userId) }

    fun getSettings(userId: Long) =
        emit { repo.getSettings(userId) }

    fun updateSettings(userId: Long, req: UpdateSettingsRequest) =
        emit { repo.updateSettings(userId, req) }
}
