package de.ljz.questify.feature.settings.presentation.screens.permissions

import kotlinx.serialization.Serializable

@Serializable
data class SettingsPermissionRoute(
    val canNavigateBack: Boolean = true
)