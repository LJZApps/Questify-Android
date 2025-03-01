package de.ljz.questify.ui.features.quests.quests_overview

import de.ljz.questify.data.shared.Points
import de.ljz.questify.domain.models.quests.QuestEntity

data class QuestOverviewUIState(
    val quests: List<QuestEntity> = listOf(),
    val isFastAddingFocused: Boolean = false,
    val fastAddingText: String = "",
    val questDoneDialogVisible: Boolean = false,
    val questDoneDialogState: QuestDoneDialogState = QuestDoneDialogState(),
    val featureSettings: FeatureSettingsState = FeatureSettingsState()
)

data class QuestDoneDialogState(
    val visible: Boolean = false,
    val questName: String = "",
    val xp: Int = 0,
    val points: Int = 0,
    val newLevel: Int = 0,
)

data class FeatureSettingsState(
    val fastQuestAddingEnabled: Boolean = true
)