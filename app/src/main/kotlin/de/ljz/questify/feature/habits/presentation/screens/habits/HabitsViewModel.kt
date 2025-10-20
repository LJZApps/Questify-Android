package de.ljz.questify.feature.habits.presentation.screens.habits

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.habits.data.models.HabitType
import de.ljz.questify.feature.habits.data.models.HabitsEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = HabitsUiState(
            isSortingBottomSheetOpen = false,
            habits = listOf(
                HabitsEntity(
                    id = 0,
                    title = "Nicht rauchen",
                    notes = "Nicht mehr rauchen, um Gesundheit zu verbessern und Geld zu sparen",
                    type = HabitType.NEGATIVE
                ),
                HabitsEntity(
                    id = 1,
                    title = "Nicht rauchen",
                    notes = "Nicht mehr rauchen, um Gesundheit zu verbessern und Geld zu sparen",
                    type = HabitType.NEGATIVE
                ),
                HabitsEntity(
                    id = 2,
                    title = "2L Wasser trinken",
                    notes = "Mehr Wasser trinken, um Gesundheit zu verbessern",
                    type = HabitType.POSITIVE
                ),
                HabitsEntity(
                    id = 3,
                    title = "Bildschirmzeit",
                    notes = "Nicht mehr als 2 Stunden Bildschirmzeit am Tag verbringen",
                    type = HabitType.POSITIVE
                ),
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(event: HabitsUiEvent) {
        when (event) {
            else -> Unit
        }
    }
}