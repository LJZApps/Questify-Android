package de.ljz.questify.ui.features.main.dialogs

data class CreateQuestDialogState(
    val title: String = "",
    val description: String = "",
    val isMainQuest: Boolean = false
)