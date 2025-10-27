package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.core.utils.MaxWidth
import de.ljz.questify.feature.quests.presentation.dialogs.CreateReminderDialog
import de.ljz.questify.feature.quests.presentation.dialogs.DeleteConfirmationDialog
import de.ljz.questify.feature.quests.presentation.sheets.SelectCategoryBottomSheet
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestDetailScreen(
    viewModel: QuestDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val categories = viewModel.categories.collectAsStateWithLifecycle().value
    val focusManager = LocalFocusManager.current

    val haptic = LocalHapticFeedback.current

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.showDeleteConfirmationDialog()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.error,
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete_outlined),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            val subQuests = uiState.questState.subQuests
            val hasSubQuests = subQuests.isNotEmpty()

            val allSubQuestsDone = !hasSubQuests || subQuests.all { it.isDone }

            val isQuestAlreadyDone = uiState.questState.done

            val isButtonEnabled = !isQuestAlreadyDone && allSubQuestsDone

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding()
                ) {
                    HorizontalDivider()
                    if (hasSubQuests && !allSubQuestsDone && !isQuestAlreadyDone) {
                        Text(
                            text = "Erledige zuerst alle Unteraufgaben.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        enabled = isButtonEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = "Quest abschließen")
                    }
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .widthIn(max = MaxWidth),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = uiState.questState.title,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )

                                Badge(
                                    containerColor = when (uiState.questState.difficulty) {
                                        Difficulty.EASY -> MaterialTheme.colorScheme.surfaceContainerLow
                                        Difficulty.MEDIUM -> MaterialTheme.colorScheme.surfaceContainer
                                        Difficulty.HARD -> MaterialTheme.colorScheme.primary
                                    },
                                    contentColor = when (uiState.questState.difficulty) {
                                        Difficulty.EASY -> MaterialTheme.colorScheme.onSurface
                                        Difficulty.MEDIUM -> MaterialTheme.colorScheme.onSurface
                                        Difficulty.HARD -> MaterialTheme.colorScheme.onPrimary
                                    }
                                ) {
                                    Text(
                                        text = when (uiState.questState.difficulty) {
                                            Difficulty.EASY -> "Leicht"
                                            Difficulty.MEDIUM -> "Mittel"
                                            Difficulty.HARD -> "Schwer"
                                        },
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }


                            uiState.questState.description
                                .takeIf { it.isNotEmpty() }
                                ?.let { description ->
                                    Text(
                                        text = uiState.questState.description
                                    )
                                }
                        }
                    }

                    uiState.questState.subQuests
                        .takeIf { it.isNotEmpty() }
                        ?.let { subQuestEntities ->
                            OutlinedCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val doneCount = subQuestEntities.count { it.isDone }
                                    val totalCount = subQuestEntities.size
                                    val progress = doneCount.toFloat() / totalCount.toFloat()
                                    val percentage = (progress * 100).toInt()

                                    val animatedProgress by animateFloatAsState(
                                        targetValue = progress,
                                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                                        label = "QuestProgressAnimation"
                                    )

                                    val animatedPercentage by animateIntAsState(
                                        targetValue = percentage,
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = FastOutLinearInEasing
                                        ),
                                        label = "QuestProgressAnimation"
                                    )

                                    Text(
                                        text = "Fortschritt",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Aktueller Fortschritt:",
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = "${animatedPercentage}%",
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    LinearProgressIndicator(
                                        progress = { animatedProgress },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(12.dp)
                                    )

                                    Text(
                                        text = "$doneCount von $totalCount Unteraufgaben erledigt",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                    uiState.questState.subQuests
                        .takeIf { it.isNotEmpty() }
                        ?.let { subQuestEntities ->
                            OutlinedCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Unteraufgaben",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Column {
                                        subQuestEntities.forEach { subQuestEntity ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable {
                                                        viewModel.checkSubQuest(
                                                            id = subQuestEntity.id,
                                                            checked = !subQuestEntity.isDone
                                                        )
                                                    }
                                                    .padding(all = 8.dp)
                                                ,
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                CompositionLocalProvider(
                                                    LocalMinimumInteractiveComponentSize provides 0.dp
                                                ) {
                                                    Checkbox(
                                                        checked = subQuestEntity.isDone,
                                                        onCheckedChange = {
                                                            viewModel.checkSubQuest(
                                                                id = subQuestEntity.id,
                                                                checked = !subQuestEntity.isDone
                                                            )
                                                        },
                                                    )
                                                }

                                                Text(
                                                    text = subQuestEntity.text,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    uiState.questState
                        .takeIf {
                            it.selectedDueDate != 0L || it.notificationTriggerTimes.isNotEmpty()
                        }
                        ?.let { questState ->
                            OutlinedCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Fälligkeit & Erinnerungen",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                    uiState.questState.selectedDueDate
                                        .takeIf { it != 0L }
                                        ?.let { dueDate ->
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.ic_calendar_month_outlined),
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )

                                                Text(
                                                    text = SimpleDateFormat(
                                                        "dd. MMMM YYYY 'um' HH:mm",
                                                        Locale.getDefault()
                                                    ).format(dueDate),
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }
                                        }

                                    uiState.questState.notificationTriggerTimes
                                        .takeIf { it.isNotEmpty() }
                                        ?.let { notifications ->
                                            HorizontalDivider(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            )

                                            Column {
                                                Text(
                                                    text = "Gesetzte Erinnerungen:",
                                                    fontSize = 14.sp
                                                )

                                                notifications.forEach { notification ->
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(R.drawable.ic_notifications_outlined),
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colorScheme.primary,
                                                            modifier = Modifier.size(16.dp)
                                                        )

                                                        Text(
                                                            text = SimpleDateFormat(
                                                                "dd. MMMM YYYY 'um' HH:mm",
                                                                Locale.getDefault()
                                                            ).format(notification),
                                                            fontSize = 14.sp
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                    TextButton(
                                        onClick = {

                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = "Erinnerungen verwalten")
                                    }
                                }
                            }
                        }


                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Belohnungen",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp, horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    RewardItem(
                                        icon = painterResource(R.drawable.star),
                                        text = "${uiState.questState.difficulty.xpValue} XP"
                                    )

                                    RewardItem(
                                        icon = painterResource(R.drawable.coins),
                                        text = "${uiState.questState.difficulty.pointsValue} Punkte"
                                    )
                                }
                            }
                        }
                    }

                    /*

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_title),
                            contentDescription = null,
                        )

                        TextField(
                            value = editQuestState.title,
                            onValueChange = {
                                viewModel.updateTitle(it)
                            },
                            label = { Text("Titel") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_notes),
                            contentDescription = null,
                        )

                        TextField(
                            value = editQuestState.description,
                            onValueChange = {
                                viewModel.updateDescription(it)
                            },
                            label = { Text("Notizen") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Schwierigkeit",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                        ) {
                            // Beispiel: unterschiedliche Gewichte pro Button
                            val modifiers = List(options.size) { Modifier.weight(1f) }

                            options.forEachIndexed { index, label ->
                                ToggleButton(
                                    checked = editQuestState.difficulty == index,
                                    onCheckedChange = {
                                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                        viewModel.updateDifficulty(index)
                                    },
                                    modifier = modifiers[index].semantics {
                                        role = Role.RadioButton
                                    },
                                    shapes = when (index) {
                                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                        options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                    },
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        val tint = if (editQuestState.difficulty == index)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant

                                        when (index) {
                                            0 -> EasyIcon(tint = tint)
                                            1 -> MediumIcon(tint = tint)
                                            2 -> HardIcon(tint = tint)
                                            3 -> EpicIcon(tint = tint)
                                        }

                                        Text(label)
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Liste",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val isFocused: Boolean by interactionSource.collectIsFocusedAsState()

                            LaunchedEffect(isFocused) {
                                if (isFocused) {
                                    viewModel.showSelectCategoryDialog()
                                }
                            }

                            Icon(
                                painter = painterResource(R.drawable.ic_label_outlined),
                                contentDescription = null,
                            )

                            TextField(
                                value = selectedCategory?.text ?: "",
                                onValueChange = {},
                                label = { Text("Liste") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        painter = if (isFocused)
                                            painterResource(R.drawable.ic_keyboard_arrow_up)
                                        else
                                            painterResource(R.drawable.ic_keyboard_arrow_down),
                                        contentDescription = null
                                    )
                                },
                                interactionSource = interactionSource
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Zeitplanung",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val isFocused: Boolean by interactionSource.collectIsFocusedAsState()
                            val date = Date(editQuestState.selectedDueDate)
                            val formattedDate = dateFormat.format(date)

                            LaunchedEffect(isFocused) {
                                if (isFocused) {
                                    viewModel.showDueDateSelectionDialog()
                                }
                            }

                            Icon(
                                painter = painterResource(R.drawable.ic_schedule_outlined),
                                contentDescription = null,
                            )

                            TextField(
                                value = if (editQuestState.selectedDueDate.toInt() == 0) "" else formattedDate,
                                onValueChange = {},
                                label = { Text("Fälligkeit") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        painter = if (isFocused)
                                            painterResource(R.drawable.ic_keyboard_arrow_up)
                                        else
                                            painterResource(R.drawable.ic_keyboard_arrow_down),
                                        contentDescription = null
                                    )
                                },
                                interactionSource = interactionSource
                            )

                            if (editQuestState.selectedDueDate.toInt() != 0) {
                                FilledTonalIconButton(
                                    onClick = {
                                        viewModel.setDueDate(0)
                                    },
                                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_remove),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Erinnerungen",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                                    .clickable {
                                        viewModel.showCreateReminderDialog()
                                    }
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = "Erinnerung hinzufügen",
                                    modifier = Modifier.size(18.dp)
                                )

                                Text("Hinzufügen")
                            }

                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            editQuestState.notificationTriggerTimes.sorted()
                                .forEachIndexed { index, triggerTime ->
                                    FilterChip(
                                        selected = false,
                                        onClick = {
                                            viewModel.removeReminder(index)
                                        },
                                        label = { Text(dateFormat.format(Date(triggerTime))) },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_notifications_filled),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(18.dp)
                                            )
                                        },
                                        colors = FilterChipDefaults.elevatedFilterChipColors()
                                    )
                                }
                        }
                    }*/
                }
            }

            // Dialogs
            if (uiState.dialogState == DialogState.CreateReminder) {
                CreateReminderDialog(
                    onDismiss = viewModel::hideCreateReminderDialog,
                    onConfirm = { timestamp ->
                        viewModel.addReminder(timestamp)
                        viewModel.hideCreateReminderDialog()
                    },
                    addingDateTimeState = uiState.addingReminderDateTimeState,
                    onReminderStateChange = { viewModel.updateReminderState(it) }
                )
            }

            if (uiState.dialogState == DialogState.SelectCategory) {
                SelectCategoryBottomSheet(
                    categories = categories,
                    onCategorySelect = { category ->
                        viewModel.selectCategory(category)
                        viewModel.hideSelectCategoryDialog()
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        viewModel.hideSelectCategoryDialog()
                        focusManager.clearFocus()
                    },
                    onCreateCategory = { text ->
                        viewModel.addQuestCategory(text)
                    }
                )
            }

            if (uiState.dialogState == DialogState.DeleteConfirmation) {
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
                    onDismiss = {
                        viewModel.hideDeleteConfirmationDialog()
                    }
                )
                /*ConfirmationBottomSheet(
                    onDismissRequest = {

                    },
                    onConfirm = {

                    },
                    title = "Quest löschen?",
                    confirmationButtonText = stringResource(R.string.delete),
                    confirmationButtonColors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    dismissButtonText = stringResource(R.string.cancel),
                    text = stringResource(R.string.delete_confirmation_dialog_description),
                )*/
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .imePadding()
                    .navigationBarsPadding()
            )
        }
    )
}

@Composable
private fun RewardItem(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}