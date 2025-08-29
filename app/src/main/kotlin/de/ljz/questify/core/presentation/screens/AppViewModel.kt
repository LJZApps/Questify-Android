package de.ljz.questify.core.presentation.screens

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.R
import de.ljz.questify.core.data.shared_preferences.SessionManager
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val _isAppReady = MutableStateFlow(false)
    val isAppReady: StateFlow<Boolean> = _isAppReady.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                val appSettings = appSettingsRepository.getAppSettings().firstOrNull()

                _uiState.update {
                    it.copy(
                        isLoggedIn = sessionManager.isAccessTokenPresent(),
                        isSetupDone = appSettings?.onboardingState == true
                    )
                }

                _isAppReady.update { true }
            }
        }
    }

    fun createNotificationChannel(context: Context) {
        val groupId = "quest_group"
        val groupName = context.getString(R.string.notification_channel_quests_title)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val questGroup = NotificationChannelGroup(groupId, groupName)
        notificationManager.createNotificationChannelGroup(questGroup)

        val soundUri =
            ("android.resource://" + context.packageName + "/" + R.raw.quest_notification).toUri()

        val channel = NotificationChannel(
            "quests",
            context.getString(R.string.notification_channel_reminder_title),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_reminders_description)
            importance = NotificationManager.IMPORTANCE_HIGH
            group = groupId
            setSound(soundUri, Notification.AUDIO_ATTRIBUTES_DEFAULT)
        }

        notificationManager.createNotificationChannel(channel)
    }
}
