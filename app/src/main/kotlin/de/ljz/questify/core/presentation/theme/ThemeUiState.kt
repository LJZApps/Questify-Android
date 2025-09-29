package de.ljz.questify.core.presentation.theme

import de.ljz.questify.feature.settings.data.models.ThemeBehavior

data class ThemeUiState(
    val themeBehavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD
)
