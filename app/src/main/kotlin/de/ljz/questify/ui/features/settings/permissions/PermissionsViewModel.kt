package de.ljz.questify.ui.features.settings.permissions

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.util.isAlarmPermissionGranted
import de.ljz.questify.util.isNotificationPermissionGranted
import de.ljz.questify.util.isOverlayPermissionGranted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor() : ViewModel() {
    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var overlayPermissionLauncher: ActivityResultLauncher<Intent>
    private lateinit var alarmPermissionLauncher: ActivityResultLauncher<Intent>

    private val _uiState = MutableStateFlow(PermissionsUiState())
    val uiState = _uiState.asStateFlow()

    fun initializePermissionLauncher(
        notificationLauncher: ActivityResultLauncher<String>,
        overlayLauncher: ActivityResultLauncher<Intent>,
        alarmLauncher: ActivityResultLauncher<Intent>
    ) {
        notificationPermissionLauncher = notificationLauncher
        overlayPermissionLauncher = overlayLauncher
        alarmPermissionLauncher = alarmLauncher
    }

    fun loadPermissionData(context: Context) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isNotificationPermissionGiven = isNotificationPermissionGranted(context),
                    isAlarmPermissionsGiven = isAlarmPermissionGranted(context),
                    isOverlayPermissionsGiven = isOverlayPermissionGranted(context)
                )
            }
        }
    }

    fun requestNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun requestAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
                alarmPermissionLauncher.launch(intent)
            }
        }
    }

    fun requestOverlayPermission(context: Context) {
        if (!Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            overlayPermissionLauncher.launch(intent)
        }
    }
}
