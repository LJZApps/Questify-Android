package de.ljz.questify.feature.settings.presentation.screens.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import kotlinx.coroutines.flow.collectLatest

/**
 * Die Composable-Funktion für den Berechtigungs-Screen.
 *
 * Diese Funktion ist zustandslos und wird vollständig vom [PermissionsViewModel] gesteuert.
 * Sie ist verantwortlich für die Darstellung der UI und das Starten der Berechtigungs-Launcher.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel = hiltViewModel(),
    mainNavController: NavHostController,
    canNavigateBack: Boolean = true
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Launcher für die verschiedenen Berechtigungsanfragen und Intents
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            // Nach der Anfrage den Status neu laden
            viewModel.loadPermissionData(context)
        }
    )

    val intentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            // Nach Rückkehr aus den Einstellungen den Status neu laden
            viewModel.loadPermissionData(context)
        }
    )

    // Beim ersten Start den Berechtigungsstatus laden
    LaunchedEffect(key1 = Unit) {
        viewModel.loadPermissionData(context)

        // Auf Events vom ViewModel hören (z.B. um einen Launcher zu starten)
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PermissionEvent.LaunchIntent -> intentLauncher.launch(event.intent)
                is PermissionEvent.NavigateToSettings -> intentLauncher.launch(event.intent)
                is PermissionEvent.RequestPermission -> notificationPermissionLauncher.launch(event.permission)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.permission_screen_title)) },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { mainNavController.navigateUp() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            // Button zum Neustarten der App nur anzeigen, wenn man nicht zurück navigieren kann (z.B. beim First-Setup)
            if (!canNavigateBack) {
                Button(
                    onClick = { restartApp(context) },
                    enabled = uiState.permissionItems.all { it.isGranted },
                    shapes = ButtonDefaults.shapes(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = stringResource(R.string.permissions_screen_restart_app))
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.permissions_screen_description),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(uiState.permissionItems) { item ->
                PermissionCard(permissionItem = item)
            }
        }
    }
}

/**
 * Eine wiederverwendbare Composable zur Darstellung einer einzelnen Berechtigungskarte.
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun PermissionCard(permissionItem: PermissionItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = permissionItem.icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(permissionItem.titleResId),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(permissionItem.descriptionResId),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (permissionItem.isGranted) {
                    Text(
                        text = stringResource(R.string.permissions_screen_button_granted),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Button(
                        onClick = permissionItem.requestAction,
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text(stringResource(R.string.permissions_screen_button_grant))
                    }
                }
            }
        }
    }
}

/**
 * Startet die App neu. Nützlich nach dem Erteilen aller Berechtigungen im First-Setup-Flow.
 */
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
