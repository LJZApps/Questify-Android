package de.ljz.questify.feature.habits.presentation.screens.habits

sealed interface HabitsUiEvent {
    object OnNavigateToCreateHabitScreen : HabitsUiEvent
    object OnToggleDrawer : HabitsUiEvent

    data class OnIncrease(val id: Int) : HabitsUiEvent
}