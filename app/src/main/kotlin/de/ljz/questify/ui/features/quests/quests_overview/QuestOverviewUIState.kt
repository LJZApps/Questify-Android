package de.ljz.questify.ui.features.quests.quests_overview

import de.ljz.questify.domain.models.quests.QuestEntity

data class QuestOverviewUIState(
    val quests: List<QuestEntity> = listOf(),
    val isFastAddingFocused: Boolean = false,
    val fastAddingText: String = "",
    val featureSettings: FeatureSettingsState = FeatureSettingsState()
)

data class FeatureSettingsState(
    val fastQuestAddingEnabled: Boolean = true
)