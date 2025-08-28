package de.ljz.questify.feature.habits.presentation.screens.create_habit

import de.ljz.questify.feature.habits.data.models.HabitType

sealed interface CreateHabitUiEvent {
    data class OnTitleChange(val title: String) : CreateHabitUiEvent

    data class OnNotesChange(val notes: String) : CreateHabitUiEvent

    data class OnTypeChange(val type: HabitType) : CreateHabitUiEvent
}