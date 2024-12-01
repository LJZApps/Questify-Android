package de.ljz.questify.ui.features.quests.questdetail

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
import de.ljz.questify.domain.repositories.QuestRepository
import de.ljz.questify.ui.features.quests.questdetail.navigation.QuestDetail
import de.ljz.questify.util.trimToNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            val quest = questRepository.suspendGetQuestById(questId)
            val notificationEntities = questNotificationRepository.getNotificationsByQuestId(quest.id)
            val notifications = notificationEntities
                .filter { it.notified == false }
                .map { it.notifyAt.time }

            _uiState.value = _uiState.value.copy(
                questId = quest.id,
                title = quest.title,
                description = quest.notes ?: "",
                notificationTriggerTimes = notifications,
                selectedDueDate = if (quest.dueDate != null) quest.dueDate.time else 0,
                difficulty = if (quest.difficulty != null) quest.difficulty.ordinal else 0
            )
        }
    }

    fun updateQuest(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            questRepository.updateQuest(
                id = _uiState.value.questId,
                title = _uiState.value.title,
                description = _uiState.value.description.trimToNull()
            )

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

            _uiState.value.notificationTriggerTimes.forEach { notificationTriggerTime ->
                val questNotification = QuestNotificationEntity(
                    questId = questId.toInt(),
                    notifyAt = Date(notificationTriggerTime)
                )

                questNotificationRepository.addQuestNotification(questNotification)
            }

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
        val updatedTimes = _uiState.value.notificationTriggerTimes.toMutableList().apply {
            removeAt(index)
        }
        _uiState.value = _uiState.value.copy(notificationTriggerTimes = updatedTimes)
    }

    fun addReminder(timestamp: Long) {
        val updatedTimes = _uiState.value.notificationTriggerTimes.toMutableList().apply {
            add(timestamp)
        }
        _uiState.value = _uiState.value.copy(notificationTriggerTimes = updatedTimes)

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

    fun updateTitle(title: String) = updateUiState { copy(title = title) }
    fun updateDescription(description: String) = updateUiState { copy(description = description) }
    fun showDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = true) }
    fun hideDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = false) }
    fun showDeleteConfirmationDialog() =
        updateUiState { copy(isDeleteConfirmationDialogVisible = true) }

    fun hideDeleteConfirmationDialog() =
        updateUiState { copy(isDeleteConfirmationDialogVisible = false) }
}