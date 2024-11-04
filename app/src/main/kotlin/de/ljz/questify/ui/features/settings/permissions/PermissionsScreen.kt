package de.ljz.questify.ui.features.settings.permissions

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel,
    mainNavController: NavHostController,
    canNavigateBack: Boolean = true
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val activity = (LocalContext.current) as ComponentActivity

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "App-Berechtigungen") },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(
                            onClick = {
                                mainNavController.navigateUp()
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                        }
                    }
                }
            )
        },
        bottomBar = {
            Text(
                text = "Bitte starte die App neu, wenn du alle Berechtigungen zugelassen hast.",
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        },
        content = { innerPadding ->
            Column (
                modifier = Modifier.padding(innerPadding)
            ) {
                SettingsSwitch(
                    state = uiState.isNotificationPermissionGiven,
                    title = { Text(text = "Benachrichtigungen") },
                    subtitle = { Text(text = "Damit du keine wichtigen Quests verpasst!") },
                    icon = { Icon(Icons.Outlined.Notifications, contentDescription = null) },
                    enabled = !uiState.isNotificationPermissionGiven,
                    onCheckedChange = {
                        viewModel.requestNotificationPermission(context)
                    },
                )

                SettingsSwitch(
                    state = uiState.isOverlayPermissionsGiven,
                    title = { Text(text = "Über anderen Apps anzeigen") },
                    subtitle = { Text(text = "Damit du sorglos eine erneute Erinnerung einrichten kannst.") },
                    icon = { Icon(Icons.Outlined.Layers, contentDescription = null) },
                    enabled = !uiState.isOverlayPermissionsGiven,
                    onCheckedChange = {
                        viewModel.requestOverlayPermission(context)
                    },
                )

                SettingsSwitch(
                    state = uiState.isAlarmPermissionsGiven,
                    title = { Text(text = "Alarme setzen") },
                    subtitle = { Text(text = "Ermöglicht uns, dich für Quests zur richtigen Zeit zu erinnern.") },
                    icon = { Icon(Icons.Outlined.Alarm, contentDescription = null) },
                    enabled = !uiState.isAlarmPermissionsGiven,
                    onCheckedChange = {
                        viewModel.requestAlarmPermission(context)
                    },
                )
            }
        }
    )
}