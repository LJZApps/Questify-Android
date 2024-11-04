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

private const val REQUEST_NOTIFICATION_PERMISSION = 1001

@HiltViewModel
class PermissionsViewModel @Inject constructor(

): ViewModel() {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val _uiState = MutableStateFlow(PermissionsUiState())
    val uiState = _uiState.asStateFlow()

    fun initializePermissionLauncher(permissionsLauncherFromActivity: ActivityResultLauncher<String>) {
        permissionLauncher = permissionsLauncherFromActivity
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
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
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