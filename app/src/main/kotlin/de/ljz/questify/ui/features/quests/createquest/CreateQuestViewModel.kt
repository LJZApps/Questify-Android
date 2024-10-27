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
): ViewModel() {
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

//            scheduleNotification(context, questId.toInt(), "Zeit für eine Quest!", _uiState.value.title)

            onSuccess.invoke()
        }

        /*val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (alarmManager.canScheduleExactAlarms() || Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {

        } else {
            showAlertManagerInfo()
        }*/
    }

    private fun scheduleNotification(context: Context, id: Int, title: String, description: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, QuestNotificationReceiver::class.java).apply {
            putExtra("notificationId", id)
            putExtra("title", title)
            putExtra("description", description)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = System.currentTimeMillis() + 10 * 1000

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    _uiState.value.selectedTime,
                    pendingIntent
                )
            } else {
                showAlertManagerInfo()
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
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