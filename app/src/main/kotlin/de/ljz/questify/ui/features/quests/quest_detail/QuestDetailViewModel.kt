package de.ljz.questify.ui.features.quests.quest_detail

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.application.AddingReminderState
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.quests.QuestRepository
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.util.trimToNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val questDetailRoute = savedStateHandle.toRoute<QuestDetail>()
    val questId = questDetailRoute.id

    init {
        viewModelScope.launch {
            val questFlow = questRepository.getQuestByIdFlow(questId)

            questFlow.collectLatest { quest ->
                // Do not remove "?" for null safety - YES it can be null
                quest?.let {
                    val notificationEntities =
                        questNotificationRepository.getNotificationsByQuestId(it.id)
                    val notifications = notificationEntities
                        .filter { !it.notified }
                        .map { it.notifyAt.time }

                    _uiState.value = _uiState.value.copy(
                        editQuestState = _uiState.value.editQuestState.copy(
                            title = it.title,
                            description = it.notes ?: "",
                            difficulty = it.difficulty.ordinal,
                            notificationTriggerTimes = notifications,
                        ),
                        questState = _uiState.value.questState.copy(
                            questId = it.id,
                            title = it.title,
                            description = it.notes ?: "",
                            notificationTriggerTimes = notifications,
                            selectedDueDate = if (it.dueDate != null) it.dueDate.time else 0,
                            difficulty = it.difficulty.ordinal,
                            isQuestDone = it.done,
                        )
                    )
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
                id = _uiState.value.questState.questId,
                title = _uiState.value.editQuestState.title,
                description = _uiState.value.editQuestState.description.trimToNull()
            )

            onSuccess.invoke()
        }
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

            questRepository.deleteQuest(id = questId)
            questNotificationRepository.removeNotifications(questId)

            onSuccess.invoke()
        }
    }

    fun removeReminder(index: Int) {
        val updatedTimes = _uiState.value.editQuestState.notificationTriggerTimes.toMutableList().apply {
            removeAt(index)
        }
        _uiState.value = _uiState.value.copy(
            editQuestState = _uiState.value.editQuestState.copy(
                notificationTriggerTimes = updatedTimes
            )
        )
    }

    fun addReminder(timestamp: Long) {
        val updatedTimes = _uiState.value.editQuestState.notificationTriggerTimes.toMutableList().apply {
            add(timestamp)
        }
        _uiState.value = _uiState.value.copy(
            editQuestState = _uiState.value.editQuestState.copy(
                notificationTriggerTimes = updatedTimes
            )
        )

        updateUiState {
            copy(
                isAddingReminder = true,
                addingReminderState = AddingReminderState.DATE
            )
        }
    }

    fun updateReminderState(reminderState: AddingReminderState) {
        updateUiState {
            copy(addingReminderState = reminderState)
        }
    }

    private fun updateUiState(update: QuestDetailUiState.() -> QuestDetailUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun showCreateReminderDialog() = updateUiState {
        copy(
            isAddingReminder = true,
            addingReminderState = AddingReminderState.DATE
        )
    }

    fun hideCreateReminderDialog() = updateUiState {
        copy(
            isAddingReminder = false,
            addingReminderState = AddingReminderState.NONE
        )
    }

    fun updateTitle(title: String) =
        updateUiState { copy(editQuestState = editQuestState.copy(title = title)) }

    fun updateDescription(description: String) =
        updateUiState { copy(editQuestState = editQuestState.copy(description = description)) }

    fun expandTrophySection() = updateUiState { copy(trophiesExpanded = true) }
    fun hideTrophySection() = updateUiState { copy(trophiesExpanded = false) }

    fun toggleTrophySection() = updateUiState { copy(trophiesExpanded = !trophiesExpanded) }

    fun showRemindersBottomSheet() = updateUiState { copy(isShowingReminderBottomSheet = true) }
    fun hideReminderBottomSheet() = updateUiState { copy(isShowingReminderBottomSheet = false) }

    fun showDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = true) }
    fun hideDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = false) }
    fun startEditMode() = updateUiState {
        copy(
            isEditingQuest = true,
            editQuestState = editQuestState.copy(
                title = questState.title,
                description = questState.description,
                difficulty = questState.difficulty
            )
        )
    }
    fun stopEditMode() = updateUiState {
        copy(
            isEditingQuest = false
        )
    }

    fun showDeleteConfirmationDialog() =
        updateUiState { copy(isDeleteConfirmationDialogVisible = true) }

    fun hideDeleteConfirmationDialog() =
        updateUiState { copy(isDeleteConfirmationDialogVisible = false) }
}