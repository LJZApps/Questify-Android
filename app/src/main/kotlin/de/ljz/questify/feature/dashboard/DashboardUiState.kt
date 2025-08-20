package de.ljz.questify.feature.dashboard

import de.ljz.questify.core.utils.changelog.ChangeLog
import de.ljz.questify.domain.datastore.AppUser
import de.ljz.questify.feature.quests.domain.models.QuestEntity

data class DashboardUiState(
    val quests: List<QuestEntity> = listOf(),
    val changelogVisible: Boolean = false,
    val newVersionVisible: Boolean = false,
    val changelog: ChangeLog? = null,
    val appUser: AppUser = AppUser()
)
