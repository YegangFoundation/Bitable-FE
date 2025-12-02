package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class VoiceCommandResponse(
    val message: String,
    val intent: String,          // 서버 enum 그대로 String으로 받음
    val actionResult: String? = null
)