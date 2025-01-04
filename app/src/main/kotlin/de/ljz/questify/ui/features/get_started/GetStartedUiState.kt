package de.ljz.questify.ui.features.get_started

data class GetStartedUiState(
    val currentIndex: Int = 0,
    val currentText: String = "",
    val showContinueHint: Boolean = false,
    val showButton: Boolean = false,
    val isNotificationPermissionGranted: Boolean = false,
    val isAlarmPermissionGranted: Boolean = false,
    val isOverlayPermissionGranted: Boolean = false
)