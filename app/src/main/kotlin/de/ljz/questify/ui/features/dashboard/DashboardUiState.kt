package de.ljz.questify.ui.features.dashboard

import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.util.changelog.ChangeLog

data class DashboardUiState(
    val quests: List<QuestEntity> = listOf(),
    val userPoints: Int = 0,
    val userXp: Int = 0,
    val userLevel: Int = 0,
    val changelogVisible: Boolean = false,
    val newVersionVisible: Boolean = false,
    val changelog: ChangeLog? = null
)
