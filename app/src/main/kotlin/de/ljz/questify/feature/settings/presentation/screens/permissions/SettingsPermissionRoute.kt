package de.ljz.questify.feature.settings.presentation.screens.permissions

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class SettingsPermissionRoute(
    val backNavigationEnabled: Boolean = true
) : NavKey