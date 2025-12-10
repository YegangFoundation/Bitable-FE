package com.example.bitable_fe.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.repository.iface.VoiceRepository
import com.example.bitable_fe.core.network.request.VoiceCommandRequest
import com.example.bitable_fe.core.ui.state.VoiceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class VoiceViewModel @Inject constructor(
    private val repo: VoiceRepository
) : ViewModel() {

    private val _state = MutableStateFlow<VoiceUiState>(VoiceUiState.Idle)
    val state = _state.asStateFlow()

    fun processCommand(req: VoiceCommandRequest) =
        emit { repo.processCommand(req) }

    fun uploadAudio(userId: Long, audio: MultipartBody.Part) =
        emit { repo.uploadAudio(userId, audio) }

    fun tts(text: String) =
        emit { repo.tts(text) }

    fun sendRecordedAudio(userId: Long, file: File) {
        viewModelScope.launch {
            _state.value = VoiceUiState.Loading

            val requestFile = file.asRequestBody("audio/mp4".toMediaType())
            val part = MultipartBody.Part.createFormData("file", file.name, requestFile)

            runCatching { repo.uploadAudio(userId, part) }
                .onSuccess { _state.value = VoiceUiState.Success(it) }
                .onFailure { _state.value = VoiceUiState.Error(it.message ?: "녹음 업로드 실패") }
        }
    }


    private fun emit(block: suspend () -> Any) {
        viewModelScope.launch {
            _state.value = VoiceUiState.Loading
            runCatching { block() }
                .onSuccess { _state.value = VoiceUiState.Success(it) }
                .onFailure { _state.value = VoiceUiState.Error(it.message ?: "") }
        }
    }

    fun clearState() {
        _state.value = VoiceUiState.Idle
    }
}
