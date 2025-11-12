package de.ljz.questify.feature.settings.presentation.screens.permissions

import de.ljz.questify.core.presentation.navigation.AppNavKey
import kotlinx.serialization.Serializable

@Serializable
data class SettingsPermissionRoute(
    val backNavigationEnabled: Boolean = true
) : AppNavKey