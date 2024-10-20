package de.ljz.questify.ui.features.createquest

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
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import de.ljz.questify.data.repositories.QuestRepository
import de.ljz.questify.data.shared.Points
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateQuestViewModel @Inject constructor(
    private val questRepository: QuestRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CreateQuestUiState())
    val uiState: StateFlow<CreateQuestUiState> = _uiState.asStateFlow()

    fun createQuest(context: Context) {
        val quest = MainQuestEntity(
            title = _uiState.value.title,
            description = _uiState.value.description,
            points = Points.EASY,
            createdAt = Date()
        )

        viewModelScope.launch {
            val questId = questRepository.addMainQuest(quest)

            scheduleNotification(context, questId.toInt(), "Zeit für deine Quest!", _uiState.value.title)
        }
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
                    triggerTime,
                    pendingIntent
                )
            } else {
                requestExactAlarmPermission(context)
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    private fun requestExactAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updateDifficulty(difficulty: Int) {
        _uiState.value = _uiState.value.copy(difficulty = difficulty)
    }
}