package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class UserSettingsResponse(
    val ttsSpeed: Double? = null,
    val voiceType: String? = null,
    val infoLevel: Int? = null
)

@Serializable
data class UserResponse(
    val userId: Long,
    val phoneNumber: String,
    val name: String,
    val defaultAccountId: Long? = null,
    val onboardingCompleted: Boolean,
    val createdAt: String
)