package de.ljz.questify.feature.settings.permissions

import kotlinx.serialization.Serializable

@Serializable
data class SettingsPermissionRoute(
    val canNavigateBack: Boolean = true
)