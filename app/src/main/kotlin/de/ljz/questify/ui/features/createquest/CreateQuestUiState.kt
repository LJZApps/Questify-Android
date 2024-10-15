package de.ljz.questify.ui.features.createquest

data class CreateQuestUiState(
    val title: String = "",
    val description: String = "",
    val difficulty: Int = 0,
)