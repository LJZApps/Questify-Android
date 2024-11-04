package de.ljz.questify.ui.features.dashboard

import de.ljz.questify.domain.models.quests.MainQuestEntity

data class DashboardUiState(
    val quests: List<MainQuestEntity> = listOf(),
    val userPoints: Int = 0,
    val dashboardOnboardingDone: Boolean = true,
    val userXp: Int = 0,
    val userLevel: Int = 0,
)
