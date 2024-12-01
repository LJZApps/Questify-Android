package de.ljz.questify.ui.features.quests.viewquests

import de.ljz.questify.domain.models.quests.QuestEntity

data class ViewQuestsUIState(
    val quests: List<QuestEntity> = listOf(),
    val createQuestSheetOpen: Boolean = false,
    val questOnboardingDone: Boolean = true
)
