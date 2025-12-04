package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.datastore.UserPreferencesDataStore
import com.example.bitable_fe.core.data.repository.iface.UserRepository
import com.example.bitable_fe.core.network.request.UpdateSettingsRequest
import com.example.bitable_fe.core.network.response.UserResponse
import com.example.bitable_fe.core.network.response.UserSettingsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val prefs: UserPreferencesDataStore
) : ViewModel() {

    // 현재 세팅값을 담는 StateFlow
    private val _settings = MutableStateFlow<UserSettingsResponse?>(null)
    val settings = _settings.asStateFlow()

    private val _users = MutableStateFlow<UserResponse?>(null)
    val users = _users.asStateFlow()

    // 간편하게 UI에서 접근할 수 있도록 분리
    val ttsSpeed = settings.map { it?.ttsSpeed ?: 1.5 }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), 1.5
    )

    val voiceType = settings.map { it?.voiceType ?: "default" }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), "default"
    )

    val infoLevel = settings.map { it?.infoLevel ?: 1 }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), 1
    )

    private var userId: Long = -1

    init {
        loadUserIdAndSettings()
    }
    private fun loadUser(){
        viewModelScope.launch {
            runCatching {
                userRepo.getUser(userId)
            }.onSuccess {
                _users.value = it
            }
        }
    }
    private fun loadUserIdAndSettings() {
        viewModelScope.launch {
            prefs.userId.collect { id ->
                if (id > 0) {
                    userId = id
                    loadSettings()
                }
            }
        }
    }

    // 🔵 서버에서 설정값 가져오기
    fun loadSettings() {
        viewModelScope.launch {
            runCatching {
                userRepo.getSettings(userId)
            }.onSuccess {
                _settings.value = it
            }.onFailure {
                // 오류 핸들링 필요하면 추가
            }
        }
    }

    // 🔵 설정 업데이트
    fun updateSettings(
        ttsSpeedValue: Double = ttsSpeed.value,
        voiceTypeValue: String = voiceType.value,
        infoLevelValue: Int = infoLevel.value
    ) {
        viewModelScope.launch {
            val req = UpdateSettingsRequest(
                ttsSpeed = ttsSpeedValue,
                voiceType = voiceTypeValue,
                infoLevel = infoLevelValue
            )

            runCatching {
                userRepo.updateSettings(userId, req)
            }.onSuccess { updated ->
                _settings.value = updated
            }
        }
    }

    // 🔵 개별 항목 변경
    fun setSpeed(speed: Double) {
        updateSettings(
            ttsSpeedValue = speed
        )
    }

    fun setInfoLevel(level: Int) {
        updateSettings(
            infoLevelValue = level
        )
    }

    fun setVoiceType(type: String) {
        updateSettings(
            voiceTypeValue = type
        )
    }
}
