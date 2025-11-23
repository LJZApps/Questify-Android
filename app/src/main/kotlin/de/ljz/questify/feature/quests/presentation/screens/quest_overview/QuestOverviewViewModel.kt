package de.ljz.questify.feature.quests.presentation.screens.quest_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.domain.use_cases.GetSortingPreferencesUseCase
import de.ljz.questify.core.domain.use_cases.SaveQuestSortingDirectionUseCase
import de.ljz.questify.core.domain.use_cases.UpdateShowCompletedQuestsUseCase
import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.domain.use_cases.AddQuestCategoryUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CancelQuestNotificationsUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CompleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestCategoryUseCase
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetAllQuestCategoriesUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetAllQuestsUseCase
import de.ljz.questify.feature.quests.domain.use_cases.UpdateQuestCategoryUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestOverviewViewModel @Inject constructor(
    private val getAllQuestsUseCase: GetAllQuestsUseCase,
    private val completeQuestUseCase: CompleteQuestUseCase,
    private val deleteQuestUseCase: DeleteQuestUseCase,

    private val getAllQuestCategoriesUseCase: GetAllQuestCategoriesUseCase,
    private val deleteQuestCategoryUseCase: DeleteQuestCategoryUseCase,
    private val addQuestCategoryUseCase: AddQuestCategoryUseCase,
    private val updateQuestCategoryUseCase: UpdateQuestCategoryUseCase,

    private val cancelQuestNotificationsUseCase: CancelQuestNotificationsUseCase,

    private val getQuestSortingPreferencesUseCase: GetSortingPreferencesUseCase,
    private val saveQuestSortingDirectionUseCase: SaveQuestSortingDirectionUseCase,
    private val updateShowCompletedQuestsUseCase: UpdateShowCompletedQuestsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = QuestOverviewUIState(
            dialogState = DialogState.None,
            allQuestPageState = AllQuestPageState(
                quests = emptyList(),
                sortingDirections = SortingDirections.ASCENDING,
                showCompleted = false
            )
        )
    )
    val uiState: StateFlow<QuestOverviewUIState> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

    private val _effect = Channel<QuestOverviewUiEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch {
                getAllQuestsUseCase().collectLatest { quests ->
                    _uiState.update { it.copy(allQuestPageState = it.allQuestPageState.copy(quests = quests)) }
                }
            }

            launch {
                getQuestSortingPreferencesUseCase()
                    .collectLatest { sortingPreferences ->
                        _uiState.update {
                            it.copy(
                                allQuestPageState = it.allQuestPageState.copy(
                                    sortingDirections = sortingPreferences.questSortingDirection,
                                    showCompleted = sortingPreferences.showCompletedQuests
                                )
                            )
                        }
                    }
            }

            launch {
                getAllQuestCategoriesUseCase().collectLatest { questCategoryEntities ->
                    _categories.value = questCategoryEntities
                }
            }
        }
    }

    fun onUiEvent(event: QuestOverviewUiEvent) {
        when (event) {
            is QuestOverviewUiEvent.OnQuestDelete -> {
                viewModelScope.launch {
                    deleteQuestUseCase.invoke(event.id)
                }
            }

            is QuestOverviewUiEvent.OnQuestChecked -> {
                viewModelScope.launch {
                    launch {
                        val result = completeQuestUseCase.invoke(event.questEntity)

                        _uiState.update {
                            it.copy(
                                dialogState = DialogState.QuestDone(
                                    questDoneDialogState = QuestDoneDialogState(
                                        xp = result.earnedXp,
                                        points = result.earnedPoints,
                                        newLevel = if (result.didLevelUp) result.newLevel else 0,
                                        questName = event.questEntity.title
                                    )
                                )
                            )
                        }
                    }

                    launch {
                        cancelQuestNotificationsUseCase.invoke(event.questEntity.id)
                    }
                }
            }

            is QuestOverviewUiEvent.ShowDialog -> {
                _uiState.update {
                    it.copy(dialogState = event.dialogState)
                }
            }

            is QuestOverviewUiEvent.CloseDialog -> {
                _uiState.update {
                    it.copy(dialogState = DialogState.None)
                }
            }

            is QuestOverviewUiEvent.CloseQuestDoneDialog -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        dialogState = DialogState.None
                    )
                }
            }

            is QuestOverviewUiEvent.ShowUpdateCategoryDialog -> {
                _uiState.update {
                    it.copy(
                        dialogState = DialogState.UpdateCategory(
                            questCategoryEntity = event.questCategoryEntity
                        )
                    )
                }
            }

            is QuestOverviewUiEvent.AddQuestCategory -> {
                viewModelScope.launch {
                    val questCategory = QuestCategoryEntity(text = event.value.trim())
                    addQuestCategoryUseCase.invoke(questCategory)
                }
            }

            is QuestOverviewUiEvent.DeleteQuestCategory -> {
                viewModelScope.launch {
                    deleteQuestCategoryUseCase.invoke(event.questCategoryEntity.id)

                    sendEffect(
                        QuestOverviewUiEffect.ShowSnackbar(
                            message = "${event.questCategoryEntity.text} gelöscht",
                            withDismissAction = true
                        )
                    )
                }
            }

            is QuestOverviewUiEvent.UpdateQuestCategory -> {
                viewModelScope.launch {
                    updateQuestCategoryUseCase.invoke(
                        id = event.questCategoryEntity.id,
                        value = event.value.trim()
                    )
                }
            }

            is QuestOverviewUiEvent.UpdateQuestSortingDirection -> {
                viewModelScope.launch {
                    saveQuestSortingDirectionUseCase.invoke(event.sortingDirections)
                }
            }

            is QuestOverviewUiEvent.UpdateShowCompletedQuests -> {
                viewModelScope.launch {
                    updateShowCompletedQuestsUseCase.invoke(value = event.value)
                }
            }

            else -> Unit
        }
    }

    private fun sendEffect(effect: QuestOverviewUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}