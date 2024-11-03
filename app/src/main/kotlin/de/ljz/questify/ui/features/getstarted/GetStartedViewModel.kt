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

    fun initializePermissionLauncher(activity: ComponentActivity) {
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            // Handle the permission result
            if (isGranted) {
                loadPermissionData(activity)
            } else {
                // Permission denied
            }
        }
    }

    fun loadPermissionData(context: Context) {
        permissionLauncher.launch(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isNotificationPermissionGranted = isNotificationPermissionGranted(context),
                    isAlarmPermissionGranted = isAlarmPermissionGranted(context),
                    isOverlayPermissionGranted = isOverlayPermissionGranted(context)
                )
            }
        }
    }

    fun onMessageCompleted() {
        viewModelScope.launch {
            val newIndex = _uiState.value.currentIndex + 1
            _uiState.update {
                it.copy(
                    currentIndex = newIndex,
                    currentText = "",
                    showContinueHint = false,
                    showButton = newIndex == messages.lastIndex
                )
            }
        }
    }

    fun setSetupDone() {
        viewModelScope.launch {
            appSettingsRepository.setOnboardingDone()
        }
    }

    private fun isNotificationPermissionGranted(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    private fun isAlarmPermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun isOverlayPermissionGranted(context: Context): Boolean {
       return Settings.canDrawOverlays(context)
    }

    fun requestNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_NOTIFICATION_PERMISSION
            )
        }

        loadPermissionData(context)
    }

    fun requestAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
                context.startActivity(intent)
            }
        }

        loadPermissionData(context)
    }

    fun requestOverlayPermission(context: Context) {
        if (!Settings.canDrawOverlays(context)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context.packageName}")
            )
            context.startActivity(intent)
        }

        loadPermissionData(context)
    }

}