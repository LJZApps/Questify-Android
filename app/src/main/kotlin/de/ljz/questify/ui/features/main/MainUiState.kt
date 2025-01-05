package de.ljz.questify.ui.features.main

data class MainUiState(
    val createQuestDialogVisible: Boolean = false,
    val userPoints: Int = 0,
    val userXP: Int = 0,
    val userLevel: Int = 0,
    val userName: String = "Abenteurer",
    val tutorialsUiState: TutorialsUiState = TutorialsUiState()
)

data class TutorialsUiState(
    val dashboardOnboardingDone: Boolean = true,
    val questsOnboardingDone: Boolean = true,
    val trophiesOnboardingDone: Boolean = true,
    val tutorialsEnabled: Boolean = true
)