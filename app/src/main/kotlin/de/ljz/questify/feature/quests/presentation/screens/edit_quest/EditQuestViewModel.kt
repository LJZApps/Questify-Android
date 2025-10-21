package de.ljz.questify.feature.quests.presentation.screens.edit_quest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetQuestByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditQuestViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getQuestByIdUseCase: GetQuestByIdUseCase,
    private val deleteQuestUseCase: DeleteQuestUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(
        value = EditQuestUiState(
            title = "",
            notes = null,
            difficulty = Difficulty.EASY,
            dueDate = 0,
            categoryId = null,

            subTasks = emptyList(),

            dueDateDialogVisible = false,
            addingDateTimeState = AddingDateTimeState.NONE
        )
    )
    val uiState = _uiState.asStateFlow()

    private val editQuestRoute = savedStateHandle.toRoute<EditQuestRoute>()
    val questId = editQuestRoute.id

    init {
        viewModelScope.launch {
            getQuestByIdUseCase.invoke(questId).let { questWithSubQuests ->
                questWithSubQuests.quest.let { questEntity ->
                    _uiState.update {
                        it.copy(
                            title = questEntity.title,
                            notes = questEntity.notes,
                            difficulty = questEntity.difficulty,
                            dueDate = questEntity.dueDate?.toInstant()?.toEpochMilli()?: 0L,
                            categoryId = questEntity.categoryId
                        )
                    }
                }

                questWithSubQuests.subTasks.let { subQuestEntities ->
                    _uiState.update {
                        it.copy(subTasks = subQuestEntities)
                    }
                }
            }
        }
    }

    fun onUiEvent(event: EditQuestUiEvent) {
        when (event) {
            is EditQuestUiEvent.OnTitleChanged -> {
                _uiState.update {
                    it.copy(title = event.value)
                }
            }

            is EditQuestUiEvent.OnNotesChanged -> {
                _uiState.update {
                    it.copy(notes = event.value)
                }
            }

            is EditQuestUiEvent.ShowAddingDueDateDialog -> {

            }

            else -> Unit
        }
    }
}