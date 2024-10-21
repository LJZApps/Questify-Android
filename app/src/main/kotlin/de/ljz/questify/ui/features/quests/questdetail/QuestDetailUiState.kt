package de.ljz.questify.ui.features.quests.questdetail

import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity

data class QuestDetailUiState (
    val questId: Int? = null,
    val quest: MainQuestEntity? = null
)