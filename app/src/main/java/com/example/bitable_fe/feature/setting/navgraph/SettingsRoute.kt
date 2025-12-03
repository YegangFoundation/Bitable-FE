package com.example.bitable_fe.feature.setting.navgraph

import kotlinx.serialization.Serializable

sealed interface SettingsRoute {

    @Serializable
    data object SettingsHostRoute : SettingsRoute

    @Serializable
    data object SettingsMainRoute : SettingsRoute

    @Serializable
    data object SpeedSettingRoute : SettingsRoute

    @Serializable
    data object InfoLevelRoute : SettingsRoute

    @Serializable
    data object CustomizeReadingRoute : SettingsRoute

    @Serializable
    data object CommandExampleRoute : SettingsRoute

    @Serializable
    data object ResetConfirmRoute : SettingsRoute
}
