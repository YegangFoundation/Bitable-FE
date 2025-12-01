package com.example.bitable_fe.feature.onboarding.navgraph

import com.example.bitable_fe.core.data.model.VoiceOption
import kotlinx.serialization.Serializable

sealed interface OnboardingRoute {
    @Serializable
    data object SplashRoute

    @Serializable
    data object LoginRoute

    @Serializable
    data class VoiceSpeedSettingRoute(
        val speed: Float = 1.0F
    )

    @Serializable
    data class VoiceInformationAmountSettingRoute(
        val initialOption: VoiceOption = VoiceOption.DETAIL
    )
}