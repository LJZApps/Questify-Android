package de.ljz.questify.ui.features.getstarted.subpages

data class GetStartedUiState(
    val currentIndex: Int = 0,
    val currentText: String = "",
    val showContinueHint: Boolean = false,
    val showButton: Boolean = false
)