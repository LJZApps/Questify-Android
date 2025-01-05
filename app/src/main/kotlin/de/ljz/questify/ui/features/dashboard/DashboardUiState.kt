package de.ljz.questify.ui.features.dashboard

import de.ljz.questify.domain.models.quests.QuestEntity

data class DashboardUiState(
    val quests: List<QuestEntity> = listOf(),
    val userPoints: Int = 0,
    val userXp: Int = 0,
    val userLevel: Int = 0,
)
