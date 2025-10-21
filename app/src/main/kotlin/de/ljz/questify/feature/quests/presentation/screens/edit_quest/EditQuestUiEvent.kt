package de.ljz.questify.feature.quests.presentation.screens.edit_quest

sealed interface EditQuestUiEvent {
    object OnNavigateUp : EditQuestUiEvent

    object ShowDatePickerDialog : EditQuestUiEvent
    object HideDatePickerDialog : EditQuestUiEvent

    object ShowTimePickerDialog : EditQuestUiEvent
    object HideTimePickerDialog : EditQuestUiEvent

    data class OnTitleChanged(val value: String) : EditQuestUiEvent
    data class OnNotesChanged(val value: String) : EditQuestUiEvent
    data class OnDueDateChanged(val value: Long) : EditQuestUiEvent

    object OnDueDateRemoved : EditQuestUiEvent
}