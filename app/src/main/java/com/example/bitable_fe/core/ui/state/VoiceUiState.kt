package com.example.bitable_fe.core.ui.state

sealed class VoiceUiState {
    object Idle : VoiceUiState()
    object Loading : VoiceUiState()
    data class Success(val data: Any) : VoiceUiState()
    data class Error(val msg: String) : VoiceUiState()
}