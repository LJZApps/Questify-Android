package de.ljz.questify.ui.features.remindagain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.R
import de.ljz.questify.core.main.AppUiState
import de.ljz.questify.data.sharedpreferences.SessionManager
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.repositories.AppSettingsRepository
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RemindAgainViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository
) : ViewModel() {

    /*private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()*/

    fun createQuestNotification(questId: Int, triggerTime: Long) {
        val questNotification = QuestNotificationEntity(
            questId = questId.toInt(),
            notifyAt = Date(triggerTime)
        )

        viewModelScope.launch {
            questNotificationRepository.addQuestNotification(questNotification)
        }
    }

}
