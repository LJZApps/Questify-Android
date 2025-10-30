package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import de.ljz.questify.feature.quests.domain.use_cases.AddQuestCategoryUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CancelQuestNotificationsUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CheckSubQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CompleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestUseCase
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestDoneDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    private val questCategoryRepository: QuestCategoryRepository,

    private val completeQuestUseCase: CompleteQuestUseCase,
    private val deleteQuestUseCase: DeleteQuestUseCase,

    private val checkSubQuestUseCase: CheckSubQuestUseCase,

    private val addQuestCategoryUseCase: AddQuestCategoryUseCase,

    private val cancelQuestNotificationsUseCase: CancelQuestNotificationsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = QuestDetailUiState(
            addingDueDateTimeState = AddingDateTimeState.NONE,
            addingReminderDateTimeState = AddingDateTimeState.NONE,
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

    private val questDetailRoute = savedStateHandle.toRoute<QuestDetailRoute>()
    val questId = questDetailRoute.id

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<QuestCategoryEntity?>(null)

    init {
        viewModelScope.launch {
            launch {
                val questFlow = questRepository.getQuestByIdFlow(questId)

                questFlow.collectLatest { quest ->
                    // Do not remove "?" for null safety - YES it can be null
                    quest?.let { questEntity ->
                        /*questEntity.copy(
                            quest = questEntity.quest.copy(
                                done = true
                            )
                        )*/

                        val notifications = questEntity.notifications
                            .filter { !it.notified }
                            .map { it.notifyAt.time }

                        val questCategoryEntity = questCategoryRepository.getQuestCategoryById(questEntity.quest.categoryId ?: 0).firstOrNull()
                        _selectedCategory.value = questCategoryEntity

                        _uiState.value = _uiState.value.copy(
                            questState = _uiState.value.questState.copy(
                                title = questEntity.quest.title,
                                description = questEntity.quest.notes ?: "",
                                difficulty = questEntity.quest.difficulty,
                                notificationTriggerTimes = notifications,
                                selectedDueDate = questEntity.quest.dueDate?.time ?: 0L,
                                done = questEntity.quest.done,
                                subQuests = questEntity.subTasks
                            ),
                            questId = questId,
                            questEntity = questEntity.quest
                        )
                    }
                }
            }

            launch {
                questCategoryRepository.getAllQuestCategories()
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

    fun addQuestCategory(text: String) {
        viewModelScope.launch {
            addQuestCategoryUseCase.invoke(
                questCategoryEntity = QuestCategoryEntity(
                    text = text
                )
            )
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

    fun selectCategory(category: QuestCategoryEntity) {
        _selectedCategory.value = category
    }

    fun deleteQuest(questId: Int, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val notifications = questNotificationRepository.getNotificationsByQuestId(questId)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            notifications.forEach { notification ->
                val intent = Intent(context, QuestNotificationReceiver::class.java)

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notification.id,
                    intent,
                    PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                if (pendingIntent != null) {
                    alarmManager.cancel(pendingIntent)
                }
            }

            deleteQuestUseCase.invoke(
                questId = questId
            )
            questNotificationRepository.removeNotifications(questId)

            onSuccess.invoke()
        }
    }

    fun addReminder(timestamp: Long) {
        val updatedTimes =
            _uiState.value.questState.notificationTriggerTimes.toMutableList().apply {
                add(timestamp)
            }
        _uiState.value = _uiState.value.copy(
            questState = _uiState.value.questState.copy(
                notificationTriggerTimes = updatedTimes
            )
        )

        updateUiState {
            copy(
                dialogState = DialogState.CreateReminder,
                addingReminderDateTimeState = AddingDateTimeState.DATE
            )
        }
    }

    fun updateReminderState(reminderState: AddingDateTimeState) {
        updateUiState {
            copy(addingReminderDateTimeState = reminderState)
        }
    }

    private fun updateUiState(update: QuestDetailUiState.() -> QuestDetailUiState) {
        _uiState.value = _uiState.value.update()
    }


    fun hideCreateReminderDialog() = updateUiState {
        copy(
            dialogState = DialogState.None,
            addingReminderDateTimeState = AddingDateTimeState.NONE
        )
    }
    fun hideSelectCategoryDialog() = updateUiState { copy(dialogState = DialogState.None) }

    fun showDeleteConfirmationDialog() =
        updateUiState { copy(dialogState = DialogState.DeleteConfirmation) }

    fun hideDeleteConfirmationDialog() =
        updateUiState { copy(dialogState = DialogState.None) }
}