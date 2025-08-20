package de.ljz.questify.feature.settings.permissions

data class PermissionsUiState(
    val isNotificationPermissionGiven: Boolean = false,
    val isOverlayPermissionsGiven: Boolean = false,
    val isAlarmPermissionsGiven : Boolean = false
)
