package com.example.bitable_fe.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val data: T? = null,
    val error: ErrorResponse? = null
)

@Serializable
data class ErrorResponse(
    val code: String? = null,
    val message: String? = null
)