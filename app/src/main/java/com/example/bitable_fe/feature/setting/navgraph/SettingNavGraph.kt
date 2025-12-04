package com.example.bitable_fe.feature.setting.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bitable_fe.core.navigation.SettingNavigator
import com.example.bitable_fe.feature.setting.screen.CustomizeReadingScreen
import com.example.bitable_fe.feature.setting.screen.InfoLevelSettingScreen
import com.example.bitable_fe.feature.setting.screen.SettingsMainScreen
import com.example.bitable_fe.feature.setting.screen.SpeedSettingScreen

fun NavGraphBuilder.settingsInnerGraph(
    navController: NavController
) {

    /** ● 메인 설정 화면 */
    composable<SettingsRoute.SettingsMainRoute> {
        SettingsMainScreen(
            onGoSpeed = { navController.navigate(SettingsRoute.SpeedSettingRoute) },
            onGoInfoLevel = { navController.navigate(SettingsRoute.InfoLevelRoute) },
            onGoCustomize = { navController.navigate(SettingsRoute.CustomizeReadingRoute) },
            onGoCommandExample = { navController.navigate(SettingsRoute.CommandExampleRoute) },
            onResetClick = { navController.navigate(SettingsRoute.ResetConfirmRoute) }
        )
    }

    /** ● 음성 속도 설정 화면 */
    composable<SettingsRoute.SpeedSettingRoute> {
        SpeedSettingScreen(
        )
    }

    /** ● 정보 레벨 설정 화면 */
    composable<SettingsRoute.InfoLevelRoute> {
        InfoLevelSettingScreen(
        )
    }

    /** ● 읽기 항목 커스텀 */
    composable<SettingsRoute.CustomizeReadingRoute> {
        CustomizeReadingScreen(
        )
    }

    /** ● 음성 명령 예시 */

}
