package de.ljz.questify.ui.features.quests.quest_overview

import de.ljz.questify.domain.models.quests.QuestEntity

data class QuestOverviewUIState(
    val quests: List<QuestEntity> = listOf(),
    val createQuestSheetOpen: Boolean = false,
    val dailiesEnabled: Boolean = false
)
