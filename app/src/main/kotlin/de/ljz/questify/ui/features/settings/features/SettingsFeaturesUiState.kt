package de.ljz.questify.ui.features.settings.features

data class SettingsFeaturesUiState(
    val questFeaturesState: QuestFeaturesState = QuestFeaturesState()
)

data class QuestFeaturesState(
    val fastAddingEnabled: Boolean = false
)