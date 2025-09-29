package de.ljz.questify.feature.quests.presentation.screens.edit_quest

sealed interface EditQuestUiEvent {
    object OnNavigateUp : EditQuestUiEvent

    data class OnTitleChanged(val value: String) : EditQuestUiEvent
}