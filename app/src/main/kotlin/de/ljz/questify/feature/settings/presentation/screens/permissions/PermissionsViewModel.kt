package de.ljz.questify.feature.settings.presentation.screens.permissions

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Notifications
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.R
import de.ljz.questify.core.utils.isAlarmPermissionGranted
import de.ljz.questify.core.utils.isNotificationPermissionGranted
import de.ljz.questify.core.utils.isOverlayPermissionGranted
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel für den PermissionsScreen.
 *
 * Diese Klasse enthält die gesamte Geschäftslogik zur Überprüfung und Anforderung von Berechtigungen.
 * Sie entkoppelt die Logik von der UI, was die Wartbarkeit und Testbarkeit verbessert.
 */
@HiltViewModel
class PermissionsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PermissionsUiState())
    val uiState = _uiState.asStateFlow()

    // SharedFlow für einmalige Events, wie das Starten eines Intents.
    private val _eventFlow = MutableSharedFlow<PermissionEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * Lädt den aktuellen Status aller relevanten Berechtigungen und aktualisiert den UI-State.
     */
    fun loadPermissionData(context: Context) {
        viewModelScope.launch {
            val items = buildPermissionItems(context)
            _uiState.update { it.copy(permissionItems = items) }
        }
    }

    /**
     * Baut die Liste der Berechtigungs-Items für die UI.
     */
    private fun buildPermissionItems(context: Context): List<PermissionItem> {
        return listOf(
            PermissionItem(
                icon = Icons.Outlined.Notifications,
                titleResId = R.string.permissions_screen_notifications_title,
                descriptionResId = R.string.permissions_screen_notifications_description,
                isGranted = isNotificationPermissionGranted(context),
                requestAction = { requestNotificationPermission(context as Activity) }
            ),
            PermissionItem(
                icon = Icons.Outlined.Layers,
                titleResId = R.string.permissions_screen_above_other_apps_title,
                descriptionResId = R.string.permissions_screen_above_other_apps_description,
                isGranted = isOverlayPermissionGranted(context),
                requestAction = { requestOverlayPermission(context) }
            ),
            PermissionItem(
                icon = Icons.Outlined.Alarm,
                titleResId = R.string.permissions_screen_set_alarms_title,
                descriptionResId = R.string.permissions_screen_set_alarms_description,
                isGranted = isAlarmPermissionGranted(context),
                requestAction = { requestAlarmPermission(context) }
            )
        )
    }

    /**
     * Logik zur Anforderung der Benachrichtigungsberechtigung.
     * Prüft, ob eine Begründung angezeigt werden soll oder ob der Nutzer zu den App-Einstellungen geleitet werden muss.
     */
    private fun requestNotificationPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            when {
                // Wenn der Nutzer die Berechtigung bereits endgültig abgelehnt hat, leiten wir ihn zu den Einstellungen.
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) && uiState.value.permissionItems.find { it.titleResId == R.string.permissions_screen_notifications_title }?.isGranted == false -> {
                    viewModelScope.launch {
                        _eventFlow.emit(PermissionEvent.NavigateToSettings(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.parse("package:${activity.packageName}")
                            }
                        ))
                    }
                }
                // Ansonsten wird die Berechtigung normal angefragt.
                else -> {
                    viewModelScope.launch {
                        _eventFlow.emit(PermissionEvent.RequestPermission(permission))
                    }
                }
            }
        }
    }

    /**
     * Startet den Intent, um die Berechtigung "Über anderen Apps einblenden" anzufordern.
     */
    private fun requestOverlayPermission(context: Context) {
        if (!Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            viewModelScope.launch {
                _eventFlow.emit(PermissionEvent.LaunchIntent(intent))
            }
        }
    }

    /**
     * Startet den Intent, um die Berechtigung "Genaue Alarme planen" anzufordern.
     */
    private fun requestAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
                viewModelScope.launch {
                    _eventFlow.emit(PermissionEvent.LaunchIntent(intent))
                }
            }
        }
    }
}

/**
 * Repräsentiert einmalige Events, die von der UI behandelt werden sollen.
 */
sealed class PermissionEvent {
    data class LaunchIntent(val intent: Intent) : PermissionEvent()
    data class NavigateToSettings(val intent: Intent) : PermissionEvent()
    data class RequestPermission(val permission: String) : PermissionEvent()
}
