package de.ljz.questify.feature.settings.presentation.screens.features

data class SettingsFeaturesUiState(
    val questFeaturesState: QuestFeaturesState = QuestFeaturesState()
)

data class QuestFeaturesState(
    val fastAddingEnabled: Boolean = false
)