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
import de.ljz.questify.core.utils.trimToNull
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    private val questCategoryRepository: QuestCategoryRepository,
    private val deleteQuestUseCase: DeleteQuestUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = QuestDetailUiState(
            addingDueDateTimeState = AddingDateTimeState.NONE,
            addingReminderDateTimeState = AddingDateTimeState.NONE,
            isEditingQuest = false,

            dialogState = DialogState.None,

            questId = 0,

            editQuestState = EditQuestState(
                title = "",
                description = "",
                difficulty = 0,
                notificationTriggerTimes = emptyList(),
                selectedDueDate = 0
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    private val questDetailRoute = savedStateHandle.toRoute<QuestDetailRoute>()
    val questId = questDetailRoute.id

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<QuestCategoryEntity?>(null)
    val selectedCategory: StateFlow<QuestCategoryEntity?> = _selectedCategory.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                val questFlow = questRepository.getQuestByIdFlow(questId)

                questFlow.collectLatest { quest ->
                    // Do not remove "?" for null safety - YES it can be null
                    quest?.let { questEntity ->
                        val notificationEntities =
                            questNotificationRepository.getNotificationsByQuestId(questEntity.id)
                        val notifications = notificationEntities
                            .filter { !it.notified }
                            .map { it.notifyAt.time }

                        val questCategoryEntity = questCategoryRepository.getQuestCategoryById(questEntity.categoryId ?: 0).firstOrNull()
                        _selectedCategory.value = questCategoryEntity

                        _uiState.value = _uiState.value.copy(
                            editQuestState = _uiState.value.editQuestState.copy(
                                title = questEntity.title,
                                description = questEntity.notes ?: "",
                                difficulty = questEntity.difficulty.ordinal,
                                notificationTriggerTimes = notifications,
                                selectedDueDate = questEntity.dueDate?.time ?: 0L
                            ),
                            questId = questId
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

    fun updateQuest(context: Context, onSuccess: () -> Unit) {
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
            questNotificationRepository.removeNotifications(questId)

            _uiState.value.editQuestState.notificationTriggerTimes.forEach { notificationTriggerTime ->
                val questNotification = QuestNotificationEntity(
                    questId = questId,
                    notifyAt = Date(notificationTriggerTime)
                )

                questNotificationRepository.addQuestNotification(questNotification)
            }

            questRepository.updateQuest(
                id = questId,
                title = _uiState.value.editQuestState.title,
                description = _uiState.value.editQuestState.description.trimToNull(),
                difficulty = Difficulty.fromIndex(_uiState.value.editQuestState.difficulty),
                dueDate = if (_uiState.value.editQuestState.selectedDueDate.toInt() == 0) null else Date(_uiState.value.editQuestState.selectedDueDate),
                categoryId = _selectedCategory.value?.id
            )

            onSuccess.invoke()
        }
    }

    fun addQuestCategory(text: String) {
        viewModelScope.launch {
            questCategoryRepository.addQuestCategory(QuestCategoryEntity(text = text))
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

    fun removeReminder(index: Int) {
        val updatedTimes =
            _uiState.value.editQuestState.notificationTriggerTimes.toMutableList().apply {
                removeAt(index)
            }
        _uiState.value = _uiState.value.copy(
            editQuestState = _uiState.value.editQuestState.copy(
                notificationTriggerTimes = updatedTimes
            )
        )
    }

    fun addReminder(timestamp: Long) {
        val updatedTimes =
            _uiState.value.editQuestState.notificationTriggerTimes.toMutableList().apply {
                add(timestamp)
            }
        _uiState.value = _uiState.value.copy(
            editQuestState = _uiState.value.editQuestState.copy(
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

    fun setDueDate(timestamp: Long) {
        updateUiState {
            copy(
                editQuestState = editQuestState.copy(
                    selectedDueDate = timestamp
                )
            )
        }
    }

    fun updateReminderState(reminderState: AddingDateTimeState) {
        updateUiState {
            copy(addingReminderDateTimeState = reminderState)
        }
    }

    fun updateDueDateState(reminderState: AddingDateTimeState) {
        updateUiState {
            copy(addingDueDateTimeState = reminderState)
        }
    }

    private fun updateUiState(update: QuestDetailUiState.() -> QuestDetailUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun showCreateReminderDialog() = updateUiState {
        copy(
            dialogState = DialogState.CreateReminder,
            addingReminderDateTimeState = AddingDateTimeState.DATE
        )
    }

    fun hideCreateReminderDialog() = updateUiState {
        copy(
            dialogState = DialogState.None,
            addingReminderDateTimeState = AddingDateTimeState.NONE
        )
    }

    fun updateTitle(title: String) =
        updateUiState { copy(editQuestState = editQuestState.copy(title = title)) }

    fun updateDescription(description: String) =
        updateUiState { copy(editQuestState = editQuestState.copy(description = description)) }

    fun showSelectCategoryDialog() = updateUiState { copy(dialogState = DialogState.SelectCategory) }
    fun hideSelectCategoryDialog() = updateUiState { copy(dialogState = DialogState.None) }

    fun showDueDateSelectionDialog() = updateUiState {
        copy(
            dialogState = DialogState.SetDueDate,
            addingDueDateTimeState = AddingDateTimeState.DATE
        )
    }

    fun hideDueDateSelectionDialog() = updateUiState {
        copy(
            DialogState.None,
            addingDueDateTimeState = AddingDateTimeState.NONE
        )
    }

    fun updateDifficulty(difficulty: Int) =
        updateUiState { copy(editQuestState = editQuestState.copy(difficulty = difficulty)) }

    fun showDeleteConfirmationDialog() =
        updateUiState { copy(dialogState = DialogState.DeleteConfirmation) }

    fun hideDeleteConfirmationDialog() =
        updateUiState { copy(dialogState = DialogState.None) }
}