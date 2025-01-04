package de.ljz.questify.ui.features.quests.create_quest.navigation

import kotlinx.serialization.Serializable

@Serializable
data class CreateQuest (
    val type: CreateQuestType? = CreateQuestType.QUEST
)

@Serializable
enum class CreateQuestType{
    QUEST,
    ROUTINE,
    DAILY,
    HABIT
}