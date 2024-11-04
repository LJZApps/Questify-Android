package de.ljz.questify.ui.features.getstarted

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val REQUEST_NOTIFICATION_PERMISSION = 1001

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val _uiState = MutableStateFlow(GetStartedUiState())
    val uiState = _uiState.asStateFlow()

    val messages = listOf(
        "Willkommen, edler Suchender! Ich bin der Quest Master, dein weiser Begleiter auf dieser Reise.",
        "Zusammen werden wir deine Aufgaben in Quests verwandeln und verborgene Kräfte entfesseln.",
        "Questify wird deinen Alltag in ein episches Abenteuer verwandeln – voller Prüfungen und Geheimnisse.",
        "Bist du bereit, deinen Pfad zu erleuchten und dein wahres Potenzial zu entfalten?",
    )



    fun setSetupDone() {
        viewModelScope.launch {
            appSettingsRepository.setOnboardingDone()
        }
    }

}