package de.ljz.questify.ui.features.quests.questdetail

import de.ljz.questify.domain.models.quests.MainQuestEntity

data class QuestDetailUiState (
    val questId: Int? = null,
    val quest: MainQuestEntity? = null
)