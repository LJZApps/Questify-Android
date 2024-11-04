package de.ljz.questify.ui.features.quests.viewquests

import de.ljz.questify.domain.models.quests.MainQuestEntity

data class ViewQuestsUIState(
    val quests: List<MainQuestEntity> = listOf(),
    val createQuestSheetOpen: Boolean = false,
    val questOnboardingDone: Boolean = true
)
