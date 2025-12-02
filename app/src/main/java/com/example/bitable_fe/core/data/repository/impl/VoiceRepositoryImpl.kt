package com.example.bitable_fe.core.data.repository.impl

import com.example.bitable_fe.core.data.repository.iface.VoiceRepository
import com.example.bitable_fe.core.network.api.VoiceApi
import com.example.bitable_fe.core.network.request.VoiceCommandRequest
import com.example.bitable_fe.core.network.response.VoiceCommandResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class VoiceRepositoryImpl @Inject constructor(
    private val api: VoiceApi
) : VoiceRepository {

    override suspend fun processCommand(req: VoiceCommandRequest): VoiceCommandResponse =
        api.processCommand(req).data!!

    override suspend fun uploadAudio(userId: Long, audio: MultipartBody.Part): VoiceCommandResponse =
        api.processAudioCommand(userId, audio).data!!

    override suspend fun tts(text: String): List<String> =
        api.tts(mapOf("text" to text))
}
