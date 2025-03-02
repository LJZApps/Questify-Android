package de.ljz.questify.ui.features.dashboard

import de.ljz.questify.domain.datastore.AppUser
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.util.changelog.ChangeLog

data class DashboardUiState(
    val quests: List<QuestEntity> = listOf(),
    val changelogVisible: Boolean = false,
    val newVersionVisible: Boolean = false,
    val changelog: ChangeLog? = null,
    val appUser: AppUser = AppUser()
)
