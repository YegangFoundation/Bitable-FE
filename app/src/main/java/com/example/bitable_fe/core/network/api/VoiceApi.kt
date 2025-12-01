package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.VoiceCommandRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.VoiceCommandResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface VoiceApi {

    @POST("/api/voice/command")
    suspend fun processCommand(
        @Body request: VoiceCommandRequest
    ): ApiResponse<VoiceCommandResponse>

    // tts: Map<String, String> body 사용
    @POST("/api/voice/tts")
    suspend fun tts(
        @Body payload: Map<String, String>
    ): List<String> // byte[] 라서 서버 설계에 따라 조정
}