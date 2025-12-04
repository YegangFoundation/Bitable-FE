package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class SetDefaultBankAccountRequest(
    val bankAccountId: Long
)

@Serializable
data class CreateUserRequest(
    val phoneNumber: String,
    val name: String
)

@Serializable
data class UpdateSettingsRequest(
    val ttsSpeed: Double? = null,
    val voiceType: String? = null,
    val infoLevel: Int? = null
)