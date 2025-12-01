package com.example.bitable_fe.feature.onboarding.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bitable_fe.core.navigation.OnboardingNavigator
import com.example.bitable_fe.feature.onboarding.screen.LoginScreen
import com.example.bitable_fe.feature.onboarding.screen.SplashScreen
import com.example.bitable_fe.feature.onboarding.screen.VoiceInformationAmountSettingScreen
import com.example.bitable_fe.feature.onboarding.screen.VoiceSpeedSettingScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
    navigator: OnboardingNavigator
){
    composable<OnboardingRoute.SplashRoute> {
        SplashScreen()
    }

    composable<OnboardingRoute.LoginRoute> {
        LoginScreen {
            navController.navigate(OnboardingRoute.VoiceSpeedSettingRoute)
        }
    }

    composable<OnboardingRoute.VoiceSpeedSettingRoute> {
        VoiceSpeedSettingScreen {
            navController.navigate(OnboardingRoute.VoiceInformationAmountSettingRoute)
        }
    }

    composable<OnboardingRoute.VoiceInformationAmountSettingRoute> {
        VoiceInformationAmountSettingScreen {
            navigator.goToTradeScreen()
        }
    }
}