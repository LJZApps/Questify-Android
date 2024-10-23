package de.ljz.questify.core.main

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
import de.ljz.questify.data.sharedpreferences.SessionManager
import de.ljz.questify.domain.repositories.AppSettingsRepository
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
            // Vorherige Abfrage der App-Einstellungen vor der UI-Update
            val appSettings = appSettingsRepository.getAppSettings().firstOrNull()

            _uiState.update {
                it.copy(
                    isLoggedIn = sessionManager.isAccessTokenPresent(),
                    isSetupDone = appSettings?.setupDone == true
                )
            }

            _isAppReady.update { true }
        }
    }

    fun createNotificationChannel(context: Context) {
        val groupId = "quest_group"
        val groupName = "Quests"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val questGroup = NotificationChannelGroup(groupId, groupName)
        notificationManager.createNotificationChannelGroup(questGroup)

        val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.quest_notification)

        val channel = NotificationChannel("quests", "Erinnerungen", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "Erhalte Quest-Erinnerungen"
            importance = NotificationManager.IMPORTANCE_HIGH
            group = groupId
            setSound(soundUri, Notification.AUDIO_ATTRIBUTES_DEFAULT)
        }

        // Registriere den Kanal beim NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
