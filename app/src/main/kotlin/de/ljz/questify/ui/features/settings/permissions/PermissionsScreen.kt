package de.ljz.questify.ui.features.settings.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel = hiltViewModel(),
    mainNavController: NavHostController,
    canNavigateBack: Boolean = true
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalActivity.current as Activity

    // Launcher für die unterschiedlichen Berechtigungen
    val notificationPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
        viewModel.loadPermissionData(context)
    }
    val overlayPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.loadPermissionData(context)
    }
    val alarmPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.loadPermissionData(context)
    }

    LaunchedEffect(Unit) {
        viewModel.initializePermissionLauncher(
            notificationPermissionLauncher,
            overlayPermissionLauncher,
            alarmPermissionLauncher
        )
        viewModel.loadPermissionData(context)
    }

    // Datenmodell für die einzelnen Berechtigungen
    val permissionItems = listOf(
        PermissionItem(
            icon = Icons.Outlined.Notifications,
            title = stringResource(R.string.permissions_screen_notifications_title),
            description = stringResource(R.string.permissions_screen_notifications_description),
            isGranted = uiState.isNotificationPermissionGiven,
            requestAction = {
                val permission = android.Manifest.permission.POST_NOTIFICATIONS
                if (androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    notificationPermissionLauncher.launch(permission)
                } else {
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = android.net.Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                }
            }
        ),
        PermissionItem(
            icon = Icons.Outlined.Layers,
            title = stringResource(R.string.permissions_screen_above_other_apps_title),
            description = stringResource(R.string.permissions_screen_above_other_apps_description),
            isGranted = uiState.isOverlayPermissionsGiven,
            requestAction = { viewModel.requestOverlayPermission(context) }
        ),
        PermissionItem(
            icon = Icons.Outlined.Alarm,
            title = stringResource(R.string.permissions_screen_set_alarms_title),
            description = stringResource(R.string.permissions_screen_set_alarms_description),
            isGranted = uiState.isAlarmPermissionsGiven,
            requestAction = { viewModel.requestAlarmPermission(context) }
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.permission_screen_title)) },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { mainNavController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (!canNavigateBack) {
                Button(
                    onClick = { restartApp(context) },
                    enabled = permissionItems.all { it.isGranted },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = stringResource(R.string.permissions_screen_restart_app))
                }
            }
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                item {
                    // Optionaler Header mit Überschrift und kurzer Beschreibung
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.permissions_screen_description),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                items(permissionItems) { item ->
                    PermissionCardNewDesign(permissionItem = item)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionCardNewDesign(permissionItem: PermissionItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            // Header-Bereich mit Hintergrundfarbe
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = permissionItem.icon,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = permissionItem.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            // Inhalt-Bereich mit Beschreibung und Button
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = permissionItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (!permissionItem.isGranted) {
                    Button(
                        onClick = permissionItem.requestAction,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.permissions_screen_button_grant))
                    }
                } else {
                    Text(
                        text = stringResource(R.string.permissions_screen_button_granted),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}

data class PermissionItem(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val isGranted: Boolean,
    val requestAction: () -> Unit
)

private fun restartApp(context: Context) {
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    context.startActivity(intent)
    if (context is Activity) {
        context.finish()
        Runtime.getRuntime().exit(0)
    }
}