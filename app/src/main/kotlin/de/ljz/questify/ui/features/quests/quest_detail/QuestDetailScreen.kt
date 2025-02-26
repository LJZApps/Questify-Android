package de.ljz.questify.ui.features.quests.quest_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.components.tooltips.BasicPlainTooltip
import de.ljz.questify.ui.features.quests.quest_detail.components.DeleteConfirmationDialog
import de.ljz.questify.util.NavBarConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailScreen(
    viewModel: QuestDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val options = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm 'Uhr'", Locale.getDefault())

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Titel
                Text(
                    text = uiState.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                // Beschreibung
                if (uiState.description.isNotBlank()) {
                    Text(
                        text = uiState.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Schwierigkeit
                Column {
                    Text(text = "Schwierigkeit", style = MaterialTheme.typography.titleMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        when (uiState.difficulty) {
                            0 -> EasyIcon(tint = MaterialTheme.colorScheme.primary)
                            1 -> MediumIcon(tint = MaterialTheme.colorScheme.primary)
                            2 -> HardIcon(tint = MaterialTheme.colorScheme.primary)
                            3 -> EpicIcon(tint = MaterialTheme.colorScheme.primary)
                        }
                        Text(
                            text = options[uiState.difficulty],
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // Fälligkeit
                Column {
                    Text(text = "Fälligkeit", style = MaterialTheme.typography.titleMedium)
                    val dueDateText = if (uiState.selectedDueDate == 0L) {
                        "Keine Fälligkeit"
                    } else {
                        dateFormat.format(Date(uiState.selectedDueDate))
                    }
                    Text(text = dueDateText, style = MaterialTheme.typography.bodyMedium)
                }

                // Erinnerungen
                if (uiState.notificationTriggerTimes.isNotEmpty()) {
                    Column {
                        Text(text = "Erinnerungen", style = MaterialTheme.typography.titleMedium)
                        uiState.notificationTriggerTimes.sorted().forEach { triggerTime ->
                            Text(
                                text = dateFormat.format(Date(triggerTime)),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            if (uiState.isDeleteConfirmationDialogVisible) {
                DeleteConfirmationDialog(
                    onConfirm = {
                        viewModel.deleteQuest(
                            uiState.questId,
                            context,
                            onSuccess = {
                                viewModel.hideDeleteConfirmationDialog()
                                navController.navigateUp()
                            }
                        )
                    },
                    onDismiss = { viewModel.hideDeleteConfirmationDialog() }
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    BasicPlainTooltip(
                        text = "Delete quest"
                    ) {
                        IconButton(onClick = { viewModel.showDeleteConfirmationDialog() }) {
                            Icon(Icons.Outlined.DeleteOutline, contentDescription = "Löschen")
                        }
                    }
                    BasicPlainTooltip(
                        text = "Edit reminders",
                    ) {
                        IconButton(onClick = { /*viewModel.showEditReminderDialog()*/ }) {
                            Icon(Icons.Outlined.Alarm, contentDescription = "Erinnerung ändern")
                        }
                    }
                },
                floatingActionButton = {
                    BasicPlainTooltip(text = "Edit quest") {
                        FloatingActionButton(
                            onClick = { /* TODO: Zum Bearbeitungsmodus wechseln */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Outlined.Edit, contentDescription = "Bearbeiten")
                        }
                    }
                },
                modifier = Modifier.imePadding()
            )
        }
    )
}
