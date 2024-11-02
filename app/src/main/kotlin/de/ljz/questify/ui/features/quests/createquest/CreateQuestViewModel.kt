package de.ljz.questify.ui.features.quests.createquest

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.models.quests.MainQuestEntity
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

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
        val quest = MainQuestEntity(
            title = _uiState.value.title,
            description = if (_uiState.value.description.isEmpty()) null else _uiState.value.description,
            difficulty = Difficulty.fromIndex(_uiState.value.difficulty),
            createdAt = Date()
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

    fun addReminder() {
        viewModelScope.launch {
            val triggerTime = System.currentTimeMillis() + 10 * 1000

            updateUiState { copy(notificationTriggerTimes = notificationTriggerTimes + triggerTime) }
        }
    }

    fun requestExactAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        }
    }

    private fun updateUiState(update: CreateQuestUiState.() -> CreateQuestUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun updateSelectedTime(time: Long) = updateUiState { copy(selectedTime = time) }
    fun showTimePicker() = updateUiState { copy(isTimePickerVisible = true) }
    fun hideTimePicker() = updateUiState { copy(isTimePickerVisible = false) }
    fun updateTitle(title: String) = updateUiState { copy(title = title) }
    fun updateDescription(description: String) = updateUiState { copy(description = description) }
    fun updateDifficulty(difficulty: Int) = updateUiState { copy(difficulty = difficulty) }
    fun showAlertManagerInfo() = updateUiState { copy(isAlertManagerInfoVisible = true) }
    fun hideAlertManagerInfo() = updateUiState { copy(isAlertManagerInfoVisible = false) }

}