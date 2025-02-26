package de.ljz.questify.ui.features.settings.settings_features

data class SettingsFeaturesUiState(
    val questFeaturesState: QuestFeaturesState = QuestFeaturesState()
)

data class QuestFeaturesState(
    val fastAddingEnabled: Boolean = false
)