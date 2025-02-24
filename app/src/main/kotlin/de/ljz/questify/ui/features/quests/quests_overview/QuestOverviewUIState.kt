package de.ljz.questify.ui.features.quests.quests_overview

import de.ljz.questify.domain.models.quests.QuestEntity

data class QuestOverviewUIState(
    val quests: List<QuestEntity> = listOf(),
    val createQuestSheetOpen: Boolean = false,
    val dailiesEnabled: Boolean = false,
    val isFastAddingFocused: Boolean = false,
    val fastAddingText: String = "",
    val fastAddingQuestPreview: QuestEntity? = null
)
