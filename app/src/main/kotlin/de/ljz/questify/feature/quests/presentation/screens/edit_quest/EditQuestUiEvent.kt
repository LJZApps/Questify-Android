package de.ljz.questify.feature.quests.presentation.screens.edit_quest

sealed interface EditQuestUiEvent {
    object OnNavigateUp : EditQuestUiEvent

    object ShowAddingDueDateDialog : EditQuestUiEvent

    data class OnTitleChanged(val value: String) : EditQuestUiEvent
    data class OnNotesChanged(val value: String) : EditQuestUiEvent
}