package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class VoiceCommandResponse(
    val message: String,
    val intent: String, // enum 그대로 String 으로 받기
    val actionResult: String? = null
    // data: JsonObject 처럼 더 복합적으로 할 수도 있음
)