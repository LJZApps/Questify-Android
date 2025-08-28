package de.ljz.questify.feature.habits.presentation.screens.create_habit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateHabitViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateHabitUiState())
    val uiState: StateFlow<CreateHabitUiState> = _uiState.asStateFlow()

    init {

    }

    fun onUiEvent(event: CreateHabitUiEvent) {
        when (event) {
            is CreateHabitUiEvent.OnTitleChange -> {
                _uiState.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is CreateHabitUiEvent.OnNotesChange -> {
                _uiState.update {
                    it.copy(
                        notes = event.notes
                    )
                }
            }

            else -> Unit
        }
    }
}