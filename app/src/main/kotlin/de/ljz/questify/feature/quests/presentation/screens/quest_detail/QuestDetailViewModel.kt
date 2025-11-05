package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.domain.use_cases.CancelQuestNotificationsUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CheckSubQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CompleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetAllQuestCategoriesUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetQuestByIdAsFlowUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetQuestCategoryByIdUseCase
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestDoneDialogState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getQuestByIdAsFlowUseCase: GetQuestByIdAsFlowUseCase,
    private val completeQuestUseCase: CompleteQuestUseCase,
    private val deleteQuestUseCase: DeleteQuestUseCase,

    private val getAllQuestCategoriesUseCase: GetAllQuestCategoriesUseCase,
    private val getQuestCategoryByIdUseCase: GetQuestCategoryByIdUseCase,

    private val checkSubQuestUseCase: CheckSubQuestUseCase,

    private val cancelQuestNotificationsUseCase: CancelQuestNotificationsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = QuestDetailUiState(
            addingDueDateTimeState = AddingDateTimeState.DATE,
            addingReminderDateTimeState = AddingDateTimeState.DATE,
            isEditingQuest = false,

            dialogState = DialogState.None,

            questEntity = null,

            questId = 0,

            questState = QuestState(
                title = "",
                description = "",
                difficulty = Difficulty.EASY,
                notificationTriggerTimes = emptyList(),
                selectedDueDate = 0,
                done = false,
                subQuests = emptyList()
            ),
            questDoneDialogState = QuestDoneDialogState(
                questName = "",
                xp = 0,
                points = 0,
                newLevel = 0
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _uiEffects = Channel<QuestDetailUiEffect>()
    val uiEffects = _uiEffects.receiveAsFlow()

    private val questDetailRoute = savedStateHandle.toRoute<QuestDetailRoute>()
    val questId = questDetailRoute.id

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<QuestCategoryEntity?>(null)

    init {
        viewModelScope.launch {
            launch {
                val questFlow = getQuestByIdAsFlowUseCase.invoke(questId)

                questFlow.collectLatest { quest ->
                    if (quest == null) {
                        _uiEffects.send(QuestDetailUiEffect.OnNavigateUp)
                    } else {
                        val notifications = quest.notifications
                            .filter { !it.notified }
                            .map { it.notifyAt.time }

                        val questCategoryEntity = getQuestCategoryByIdUseCase.invoke(
                            quest.quest.categoryId ?: 0
                        ).firstOrNull()
                        _selectedCategory.value = questCategoryEntity

                        _uiState.value = _uiState.value.copy(
                            questState = _uiState.value.questState.copy(
                                title = quest.quest.title,
                                description = quest.quest.notes ?: "",
                                difficulty = quest.quest.difficulty,
                                notificationTriggerTimes = notifications,
                                selectedDueDate = quest.quest.dueDate?.time ?: 0L,
                                done = quest.quest.done,
                                subQuests = quest.subTasks
                            ),
                            questId = questId,
                            questEntity = quest.quest
                        )
                    }
                }
            }

            launch {
                getAllQuestCategoriesUseCase()
                    .collectLatest { questCategoryEntities ->
                        _categories.value = questCategoryEntities
                    }
            }
        }
    }

    fun completeQuest(questEntity: QuestEntity) {
        viewModelScope.launch {
            launch {
                val result = completeQuestUseCase.invoke(questEntity)

                _uiState.update {
                    it.copy(
                        dialogState = DialogState.QuestDone,
                        questDoneDialogState = it.questDoneDialogState.copy(
                            xp = result.earnedXp,
                            points = result.earnedPoints,
                            newLevel = if (result.didLevelUp) result.newLevel else 0,
                            questName = questEntity.title
                        )
                    )
                }
            }

            launch {
                cancelQuestNotificationsUseCase.invoke(questEntity.id)
            }
        }
    }

    fun checkSubQuest(id: Int, checked: Boolean) {
        viewModelScope.launch {
            checkSubQuestUseCase.invoke(
                id = id,
                checked = checked
            )
        }
    }

    fun deleteQuest(questId: Int) {
        viewModelScope.launch {
            cancelQuestNotificationsUseCase.invoke(id = questId)

            deleteQuestUseCase.invoke(questId = questId)

            _uiEffects.send(QuestDetailUiEffect.OnNavigateUp)
        }
    }

    private fun updateUiState(update: QuestDetailUiState.() -> QuestDetailUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun showDeleteConfirmationDialog() =
        updateUiState { copy(dialogState = DialogState.DeleteConfirmation) }

    fun hideDeleteConfirmationDialog() =
        updateUiState { copy(dialogState = DialogState.None) }
}