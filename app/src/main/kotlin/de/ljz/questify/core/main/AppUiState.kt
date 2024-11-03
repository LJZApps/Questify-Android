package de.ljz.questify.core.main


data class AppUiState(
    val isLoggedIn: Boolean = false,
    val isSetupDone: Boolean = false,
    val isNotificationPermissionGranted: Boolean = false,
    val isAlarmPermissionGranted: Boolean = false,
    val isOverlayPermissionGranted: Boolean = false
)