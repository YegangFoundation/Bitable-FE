package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.VoiceCommandRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.VoiceCommandResponse
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface VoiceApi {

    @POST("/api/voice/command")
    suspend fun processCommand(
        @Body request: VoiceCommandRequest
    ): ApiResponse<VoiceCommandResponse>

    @POST("/api/voice/tts")
    suspend fun tts(
        @Body payload: Map<String, String>
    ): List<String> // 실제 응답 타입은 서버 구현에 맞게 조정

    @Multipart
    @POST("/api/voice/audio")
    suspend fun processAudioCommand(
        @Part("userId") userId: Long,
        @Part audio: okhttp3.MultipartBody.Part
    ): ApiResponse<VoiceCommandResponse>
}
