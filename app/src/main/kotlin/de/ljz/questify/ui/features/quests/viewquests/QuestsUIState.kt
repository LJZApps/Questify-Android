package de.ljz.questify.ui.features.quests.viewquests

import de.ljz.questify.domain.models.quests.MainQuestEntity

data class QuestsUIState(
    val quests: List<MainQuestEntity> = listOf(),
    val userPoints: Int = 0,
    val createQuestSheetOpen: Boolean = false
)
