package com.example.bitable_fe.feature.onboarding.navgraph

import com.example.bitable_fe.core.data.model.VoiceOption
import kotlinx.serialization.Serializable

sealed interface OnboardingRoute {
    @Serializable
    data object SplashRoute : OnboardingRoute

    @Serializable
    data object LoginRoute : OnboardingRoute

    @Serializable
    data class VoiceSpeedSettingRoute(
        val speed: Float = 1.0F
    ) : OnboardingRoute

    @Serializable
    data object AccountInputRoute : OnboardingRoute

    @Serializable
    data class VoiceInformationAmountSettingRoute(
        val initialOption: VoiceOption = VoiceOption.DETAIL
    ) : OnboardingRoute
}