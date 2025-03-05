package de.ljz.questify.ui.features.settings.permissions

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsSwitch
import com.meticha.permissions_compose.AppPermission
import com.meticha.permissions_compose.rememberAppPermissionState
import de.ljz.questify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel,
    mainNavController: NavHostController,
    canNavigateBack: Boolean = true
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val permissions = rememberAppPermissionState(
        permissions = listOf(
            AppPermission(
                permission = Manifest.permission.SCHEDULE_EXACT_ALARM,
                description = "Schedule exact alarm access is needed to take photos. Please grant this permission.",
                isRequired = true
            ),
            AppPermission(
                permission = Manifest.permission.SYSTEM_ALERT_WINDOW,
                description = "System alert window access is needed for voice recording. Please grant this permission.",
                isRequired = true
            ),
            AppPermission(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                description = "Post notifications access is needed to show the contacts in the App. Please grant this permission",
                isRequired = true
            )
        )
    )

    LaunchedEffect(Unit) {
        viewModel.loadPermissionData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.permission_screen_title)) },
                navigationIcon = {
                    if (canNavigateBack)
                        IconButton(
                            onClick = {
                                mainNavController.navigateUp()
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                        }
                }
            )
        },
        bottomBar = {
            if (!canNavigateBack)
                Text(
                    text = stringResource(R.string.permissions_screen_restart_app_hint),
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                SettingsSwitch(
                    state = permissions.isGranted(Manifest.permission.POST_NOTIFICATIONS),
                    title = { Text(text = stringResource(R.string.permissions_screen_notifications_title)) },
                    subtitle = { Text(text = stringResource(R.string.permissions_screen_notifications_description)) },
                    icon = { Icon(Icons.Outlined.Notifications, contentDescription = null) },
                    enabled = !permissions.isGranted(Manifest.permission.POST_NOTIFICATIONS),
                    onCheckedChange = {
                        viewModel.requestNotificationPermission(context)
                    },
                )

                SettingsSwitch(
                    state = permissions.isGranted(Manifest.permission.SYSTEM_ALERT_WINDOW),
                    title = { Text(text = stringResource(R.string.permissions_screen_above_other_apps_title)) },
                    subtitle = { Text(text = stringResource(R.string.permissions_screen_above_other_apps_description)) },
                    icon = { Icon(Icons.Outlined.Layers, contentDescription = null) },
                    enabled = !permissions.isGranted(Manifest.permission.SYSTEM_ALERT_WINDOW),
                    onCheckedChange = {
                        viewModel.requestOverlayPermission(context)
                    },
                )

                SettingsSwitch(
                    state = permissions.isGranted(Manifest.permission.SCHEDULE_EXACT_ALARM),
                    title = { Text(text = stringResource(R.string.permissions_screen_set_alarms_title)) },
                    subtitle = { Text(text = stringResource(R.string.permissions_screen_set_alarms_description)) },
                    icon = { Icon(Icons.Outlined.Alarm, contentDescription = null) },
                    enabled = !permissions.isGranted(Manifest.permission.SCHEDULE_EXACT_ALARM),
                    onCheckedChange = {
                        viewModel.requestAlarmPermission(context)
                    },
                )
            }
        }
    )
}