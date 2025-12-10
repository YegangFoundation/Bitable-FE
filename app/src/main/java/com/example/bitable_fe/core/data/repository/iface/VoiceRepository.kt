package com.example.bitable_fe.core.data.repository.iface

import com.example.bitable_fe.core.network.request.VoiceCommandRequest
import com.example.bitable_fe.core.network.response.VoiceCommandResponse
import okhttp3.MultipartBody

interface VoiceRepository {
    suspend fun processCommand(req: VoiceCommandRequest): VoiceCommandResponse
    suspend fun uploadAudio(userId: Long, audio: MultipartBody.Part): VoiceCommandResponse
    suspend fun tts(text: String): ByteArray
}
