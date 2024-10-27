package de.ljz.questify.ui.features.dashboard

import de.ljz.questify.domain.models.quests.MainQuestEntity

data class DashboardUiState(
    val quests: List<MainQuestEntity> = listOf(),
    val userPoints: Int = 0
)
