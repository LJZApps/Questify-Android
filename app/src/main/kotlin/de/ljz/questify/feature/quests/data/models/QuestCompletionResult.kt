package de.ljz.questify.feature.quests.data.models

data class QuestCompletionResult(
    val earnedXp: Int,
    val earnedPoints: Int,
    val didLevelUp: Boolean,
    val oldLevel: Int,
    val newLevel: Int
)
