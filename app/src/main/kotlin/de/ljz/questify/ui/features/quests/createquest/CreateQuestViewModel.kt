package de.ljz.questify.ui.features.quests.createquest

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.application.AddingReminderState
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

private const val REQUEST_NOTIFICATION_PERMISSION = 1001

@HiltViewModel
class CreateQuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateQuestUiState())
    val uiState: StateFlow<CreateQuestUiState> = _uiState.asStateFlow()

    @SuppressLint("NewApi")
    fun createQuest(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        val quest = QuestEntity(
            title = _uiState.value.title,
            notes = if (_uiState.value.description.isEmpty()) null else _uiState.value.description,
            difficulty = Difficulty.fromIndex(_uiState.value.difficulty),
            createdAt = Date(),
            dueDate = if (_uiState.value.selectedDueDate.toInt() == 0) null else Date(_uiState.value.selectedDueDate)
        )

        viewModelScope.launch {
            val questId = questRepository.addMainQuest(quest)

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


    fun setDueDate(timestamp: Long) {
        updateUiState {
            copy(
                selectedDueDate = timestamp
            )
        }
    }

    fun removeDueDate() {
        updateUiState {
            copy(
                selectedDueDate = 0
            )
        }
    }

    fun updateReminderState(reminderState: AddingReminderState) {
        updateUiState {
            copy(addingReminderState = reminderState)
        }
    }

    private fun updateUiState(update: CreateQuestUiState.() -> CreateQuestUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun showCreateReminderDialog() = updateUiState { copy(isAddingReminder = true, addingReminderState = AddingReminderState.DATE) }
    fun hideCreateReminderDialog() = updateUiState { copy(isAddingReminder = false, addingReminderState = AddingReminderState.NONE) }
    fun updateTitle(title: String) = updateUiState { copy(title = title) }
    fun updateDescription(description: String) = updateUiState { copy(description = description) }
    fun updateDifficulty(difficulty: Int) = updateUiState { copy(difficulty = difficulty) }
    fun showAlertManagerInfo() = updateUiState { copy(isAlertManagerInfoVisible = true) }
    fun hideAlertManagerInfo() = updateUiState { copy(isAlertManagerInfoVisible = false) }
    fun showDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = true) }
    fun hideDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = false) }
    fun showAddingDueDateDialog() = updateUiState { copy(isAddingDueDate = true, addingReminderState = AddingReminderState.DATE) }
    fun hideAddingDueDateDialog() = updateUiState { copy(isAddingDueDate = false, addingReminderState = AddingReminderState.NONE) }

}