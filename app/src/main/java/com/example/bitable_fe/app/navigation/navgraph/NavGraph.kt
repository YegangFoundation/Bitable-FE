package com.example.bitable_fe.app.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bitable_fe.app.navigation.navigator.OnboardingNavigatorImpl
import com.example.bitable_fe.feature.onboarding.navgraph.OnboardingRoute
import com.example.bitable_fe.feature.onboarding.navgraph.onboardingNavGraph

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = OnboardingRoute.SplashRoute
    ){
        onboardingNavGraph(
            navController = navController,
            navigator = OnboardingNavigatorImpl(navController)
        )
    }
}