package com.example.bitable_fe.core.network.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val phoneNumber: String,
    val name: String
)