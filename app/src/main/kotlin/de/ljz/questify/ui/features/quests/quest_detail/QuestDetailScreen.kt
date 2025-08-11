package de.ljz.questify.ui.features.quests.quest_detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.components.CreateReminderDialog
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.components.tooltips.BasicPlainTooltip
import de.ljz.questify.ui.features.quests.quest_detail.components.DeleteConfirmationDialog
import de.ljz.questify.ui.features.quests.quest_detail.components.ListReminderBottomSheet
import de.ljz.questify.util.NavBarConfig
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestDetailScreen(
    viewModel: QuestDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val questState = uiState.questState
    val editQuestState = uiState.editQuestState

    val scope = rememberCoroutineScope()

    val options = listOf(
        stringResource(R.string.difficulty_none),
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )

    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val snackbarHostState = remember { SnackbarHostState() }

    val fabShape by animateFloatAsState(
        targetValue = if (uiState.isEditingQuest) 100f else 50f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "FABShape"
    )

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
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AnimatedContent(targetState = !uiState.isEditingQuest) { targetState ->
                    if (targetState) {
                        Text(
                            text = questState.title,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    } else {
                        OutlinedTextField(
                            value = editQuestState.title,
                            onValueChange = { viewModel.updateTitle(it) },
                            label = { Text(stringResource(R.string.text_field_quest_title)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )
                    }
                }

                AnimatedContent(targetState = !uiState.isEditingQuest) { targetState ->
                    if (targetState) {
                        if (questState.description.isNotBlank()) {
                            Text(
                                text = questState.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        OutlinedTextField(
                            value = editQuestState.description,
                            onValueChange = { viewModel.updateDescription(it) },
                            label = { Text(stringResource(R.string.text_field_quest_note)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            minLines = 2,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                            )
                        )
                    }
                }

                // Schwierigkeit
                Column {
                    Text(text = stringResource(R.string.quest_detail_screen_difficulty_title), style = MaterialTheme.typography.titleMedium)

                    AnimatedContent(targetState = !(uiState.isEditingQuest && questState.difficulty == 0)) { targetState ->
                        if (targetState) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val tintColor = MaterialTheme.colorScheme.primary
                                when (questState.difficulty) {
                                    0 -> {
                                        Icon(
                                            imageVector = Icons.Outlined.Block,
                                            contentDescription = null,
                                            tint = tintColor
                                        )
                                    }
                                    1 -> EasyIcon(tint = tintColor)
                                    2 -> MediumIcon(tint = tintColor)
                                    3 -> HardIcon(tint = tintColor)
                                    4 -> EpicIcon(tint = tintColor)
                                }
                                Text(
                                    text = options[questState.difficulty],
                                    modifier = Modifier.padding(start = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            SingleChoiceSegmentedButtonRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 0.dp)
                            ) {
                                options.forEachIndexed { index, label ->
                                    val selected = (index == editQuestState.difficulty)

                                    SegmentedButton(
                                        shape = SegmentedButtonDefaults.itemShape(
                                            index = index,
                                            count = options.size
                                        ),
                                        onClick = {
                                            viewModel.updateDifficulty(index)
                                        },
                                        selected = index == editQuestState.difficulty,
                                        icon = {}
                                    ) {
                                        val tintColor =
                                            if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.primary

                                        when (index) {
                                            0 -> {
                                                Icon(
                                                    imageVector = Icons.Outlined.Block,
                                                    contentDescription = null,
                                                    tint = tintColor
                                                )
                                            }
                                            1 -> EasyIcon(tint = tintColor)
                                            2 -> MediumIcon(tint = tintColor)
                                            3 -> HardIcon(tint = tintColor)
                                            4 -> EpicIcon(tint = tintColor)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Fälligkeit
                Column {
                    Text(text = stringResource(R.string.quest_detail_screen_due_date_title), style = MaterialTheme.typography.titleMedium)
                    val dueDateText = if (questState.selectedDueDate == 0L) {
                        stringResource(R.string.quest_detail_screen_due_date_empty)
                    } else {
                        dateFormat.format(Date(questState.selectedDueDate))
                    }
                    Text(text = dueDateText, style = MaterialTheme.typography.bodyMedium)
                }

                // Erinnerungen
                if (questState.notificationTriggerTimes.isNotEmpty()) {
                    Column {
                        Text(text = stringResource(R.string.quest_detail_screen_reminders_title), style = MaterialTheme.typography.titleMedium)
                        questState.notificationTriggerTimes.sorted().forEach { triggerTime ->
                            Text(
                                text = dateFormat.format(Date(triggerTime)),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Trophäen
                /*Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = stringResource(R.string.quest_detail_screen_trophies_title), style = MaterialTheme.typography.titleMedium)
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Text(
                                    text = getAllFilledIcons().count().toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }

                        Icon(
                            imageVector = if (uiState.trophiesExpanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    viewModel.toggleTrophySection()
                                }
                        )
                    }
                    AnimatedVisibility(uiState.trophiesExpanded) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .height(240.dp)
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                        ) {
                            items(getAllFilledIcons()) { (name, icon) ->
                                TrophyCard(
                                    icon = icon,
                                    title = name,
                                    description = "PLACEHOLDER"
                                )
                            }
                        }
                    }
                }*/

                if (uiState.isShowingReminderBottomSheet) {
                    ListReminderBottomSheet(
                        reminders = editQuestState.notificationTriggerTimes,
                        onRemoveReminder = {
                            viewModel.removeReminder(it)
                        },
                        onAddReminder = {
                            viewModel.showCreateReminderDialog()
                        },
                        onDismiss = {
                            viewModel.hideReminderBottomSheet()
                        }
                    )
                }
            }

            if (uiState.isDeleteConfirmationDialogVisible) {
                DeleteConfirmationDialog(
                    onConfirm = {
                        viewModel.deleteQuest(
                            questState.questId,
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

            if (uiState.isAddingReminder) {
                CreateReminderDialog(
                    addingReminderState = uiState.addingReminderState,
                    onDismiss = { viewModel.hideCreateReminderDialog() },
                    onConfirm = {
                        viewModel.addReminder(it)
                        viewModel.hideCreateReminderDialog()
                    },
                    onReminderStateChange = {
                        viewModel.updateReminderState(it)
                    }
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    BasicPlainTooltip(
                        text = stringResource(R.string.quest_detail_screen_tooltip_delete_quest)
                    ) {
                        IconButton(onClick = { viewModel.showDeleteConfirmationDialog() }) {
                            Icon(Icons.Outlined.DeleteOutline, contentDescription = "Delete")
                        }
                    }

                    AnimatedVisibility(
                        visible = uiState.isEditingQuest && !questState.isQuestDone,
                        exit = slideOutHorizontally(targetOffsetX = { it / -2 }) + fadeOut(),
                        enter = slideInHorizontally(initialOffsetX = { it / -2 }) + fadeIn()
                    ) {
                        BasicPlainTooltip(
                            text = stringResource(R.string.quest_detail_screen_tooltip_edit_reminders),
                        ) {
                            IconButton(onClick = { viewModel.showRemindersBottomSheet() }) {
                                Icon(Icons.Outlined.Alarm, contentDescription = "Edit reminders")
                            }
                        }
                    }
                },
                floatingActionButton = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AnimatedVisibility(
                            visible = uiState.isEditingQuest,
                            exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut(),
                            enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn()
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    viewModel.stopEditMode()
                                },
                                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                                shape = RoundedCornerShape(100f)
                            ) {
                                Icon(Icons.Outlined.Close, contentDescription = "Cancel")
                            }
                        }

                        if (!questState.isQuestDone) {
                            FloatingActionButton(
                                onClick = {
                                    if (uiState.isEditingQuest) {
                                        viewModel.updateQuest(
                                            context = context,
                                            onSuccess = {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        context.getString(R.string.quest_detail_screen_snackbar_quest_updated),
                                                        withDismissAction = true
                                                    )
                                                }
                                                viewModel.stopEditMode()
                                            }
                                        )
                                    } else {
                                        viewModel.startEditMode()
                                    }
                                },
                                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                                shape = RoundedCornerShape(fabShape)
                            ) {
                                Crossfade(
                                    targetState = uiState.isEditingQuest,
                                    label = "EditQuestIconAnimation"
                                ) { isFocused ->
                                    Icon(
                                        imageVector = if (isFocused) Icons.Filled.Save else Icons.Filled.Edit,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.imePadding()
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )
}

/*
@Composable
fun TrophyCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}*/
