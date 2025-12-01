package com.example.bitable_fe.core.network.api

import com.example.bitable_fe.core.network.request.CreateUserRequest
import com.example.bitable_fe.core.network.response.ApiResponse
import com.example.bitable_fe.core.network.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserApi {

    @POST("/api/users")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): ApiResponse<UserResponse>

    @GET("/api/users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Long
    ): ApiResponse<UserResponse>

    @PUT("/api/users/{userId}/onboarding-complete")
    suspend fun completeOnboarding(
        @Path("userId") userId: Long
    ): ApiResponse<UserResponse>
}
