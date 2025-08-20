package de.ljz.questify.feature.dashboard.presentation.screens.dashboard

import de.ljz.questify.core.utils.changelog.ChangeLog
import de.ljz.questify.feature.profile.data.models.AppUser
import de.ljz.questify.feature.quests.data.models.QuestEntity

data class DashboardUiState(
    val quests: List<QuestEntity> = listOf(),
    val changelogVisible: Boolean = false,
    val newVersionVisible: Boolean = false,
    val changelog: ChangeLog? = null,
    val appUser: AppUser = AppUser()
)
