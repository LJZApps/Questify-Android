package de.ljz.questify.ui.features.getstarted.subpages

import android.app.Activity
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.navigation.home.Home
import de.ljz.questify.util.NavBarConfig

@Composable
fun GetStartedPermissionsScreen(
    viewModel: GetStartedViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val activity = (LocalContext.current) as Activity
    val uiState = viewModel.uiState.collectAsState().value

    val isNotificationPermissionGranted = uiState.isNotificationPermissionGranted
    val isAlarmPermissionGranted = uiState.isAlarmPermissionGranted
    val isOverlayPermissionGranted = uiState.isOverlayPermissionGranted

    viewModel.loadPermissionData(context)

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    Scaffold (
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Tapferer Abenteurer, wir benötigen noch ein paar Fähigkeiten von dir, um dich voll unterstützen zu können!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left
                )

                Spacer(modifier = Modifier.height(16.dp))

                PermissionRequestItem(
                    title = "Benachrichtigungen erlauben",
                    description = "Damit du keine wichtigen Quests verpasst!",
                    onClick = {
                        viewModel.requestNotificationPermission(context)
                    },
                    enabled = !isNotificationPermissionGranted
                )

                PermissionRequestItem(
                    title = "Alarme setzen",
                    description = "Ermöglicht uns, dich für Quests zur richtigen Zeit zu erinnern.",
                    onClick = {
                        viewModel.requestAlarmPermission(context)
                    },
                    enabled = !isAlarmPermissionGranted
                )

                PermissionRequestItem(
                    title = "Über anderen Apps anzeigen",
                    description = "So bleibt Questify immer im Blick!",
                    onClick = {
                        viewModel.requestOverlayPermission(context)
                    },
                    enabled = !isOverlayPermissionGranted
                )
            }
        },
        bottomBar = {
            Button(
                onClick = {
                    navController.navigate(Home)
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Abenteuer starten")
            }
        }
    )
}

@Composable
fun PermissionRequestItem(
    title: String,
    description: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        enabled = enabled,
        onClick = {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!enabled) {
                Icon(
                    Icons.Default.Check, // Verwende ein Häkchen-Icon
                    contentDescription = "Permission granted",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}




