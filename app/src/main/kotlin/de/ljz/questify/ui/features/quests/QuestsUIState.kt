package de.ljz.questify.ui.features.quests

import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity

data class QuestsUIState(
  val quests: List<MainQuestEntity> = listOf(),
  val userPoints: Int = 0,
  val createQuestSheetOpen: Boolean = false
)
