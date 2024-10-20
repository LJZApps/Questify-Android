package de.ljz.questify.ui.features.quests.createquest

data class CreateQuestUiState(
    val title: String = "",
    val description: String = "",
    val difficulty: Int = 0,
    val isTimePickerVisible: Boolean = false,
    val selectedTime: Long = 0
)