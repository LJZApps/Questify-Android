package de.ljz.questify.feature.main.presentation.screens.main

data class MainUiState(
    val userPoints: Int = 0,
    val userXP: Int = 0,
    val userLevel: Int = 0,
    val userName: String = "Adventurer",
    val userProfilePicture: String = "",
)