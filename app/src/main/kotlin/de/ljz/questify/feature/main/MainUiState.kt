package de.ljz.questify.feature.main

import de.ljz.questify.core.utils.changelog.ChangeLog

data class MainUiState(
    val createQuestDialogVisible: Boolean = false,
    val userPoints: Int = 0,
    val userXP: Int = 0,
    val userLevel: Int = 0,
    val userName: String = "Adventurer",
    val userProfilePicture: String = "",
    val tutorialsUiState: TutorialsUiState = TutorialsUiState()
)

data class ChangelogUiState(
    val newVersionVisible: Boolean = false,
    val changelog: ChangeLog? = null,
)

data class TutorialsUiState(
    val dashboardOnboardingDone: Boolean = true,
    val questsOnboardingDone: Boolean = true,
    val trophiesOnboardingDone: Boolean = true,
    val tutorialsEnabled: Boolean = true
)