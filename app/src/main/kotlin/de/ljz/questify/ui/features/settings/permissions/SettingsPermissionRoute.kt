package de.ljz.questify.ui.features.settings.permissions

import kotlinx.serialization.Serializable

@Serializable
data class SettingsPermissionRoute(
    val canNavigateBack: Boolean = true
)