package de.ljz.questify.ui.features.home

data class HomeUiState(
    val createQuestDialogVisible: Boolean = false,
    val userPoints: Int = 0,
    val userXP: Int = 0,
    val userLevel: Int = 0,
    val userName: String = "Abenteurer"
)