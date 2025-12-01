package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: Long,
    val phoneNumber: String,
    val name: String,
    val defaultAccountId: Long? = null,
    val onboardingCompleted: Boolean,
    val createdAt: String
)