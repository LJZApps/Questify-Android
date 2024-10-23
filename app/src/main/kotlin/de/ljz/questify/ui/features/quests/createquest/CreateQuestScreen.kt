package de.ljz.questify.ui.features.quests.createquest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.components.TimePickerDialog
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.quests.createquest.components.AlertManagerInfoBottomSheet
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestScreen(
    mainNavController: NavHostController,
    viewModel: CreateQuestViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val options = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )
    val context = LocalContext.current

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )
    val sheetState = rememberModalBottomSheetState()

    QuestifyTheme (
        transparentNavBar = true
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.create_quest_top_bar_title)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { mainNavController.navigateUp() }
                        ) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        viewModel.createQuest(
                            context,
                            onSuccess = {
                                mainNavController.navigateUp()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(stringResource(R.string.create_quest_save_button))
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { viewModel.updateTitle(it) },
                    label = { Text(stringResource(R.string.create_quest_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = { viewModel.updateDescription(it) },
                    label = { Text(stringResource(R.string.create_quest_description)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    minLines = 2,
                    maxLines = 4
                )

                Column {
                    Text(
                        text = "Schwierigkeit",
                        modifier = Modifier.padding(bottom = 0.dp)
                    )

                    SingleChoiceSegmentedButtonRow (
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 0.dp)
                    ) {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ), onClick = { viewModel.updateDifficulty(index) },
                                selected = index == uiState.difficulty
                            ) { Text(label) }
                        }
                    }
                }

                Text(
                    text = "${timePickerState.hour} : ${timePickerState.minute}",
                    modifier = Modifier.clickable(
                        onClick = viewModel::showTimePicker
                    )
                )

                if (uiState.isTimePickerVisible) {
                    TimePickerDialog(
                        timePickerState = timePickerState,
                        onDismiss = viewModel::hideTimePicker,
                        onConfirm = { timestamp ->
                            viewModel.updateSelectedTime(timestamp)
                            viewModel.hideTimePicker()
                        }
                    )
                }
            }
        }

        if (uiState.isAlertManagerInfoVisible) {
            AlertManagerInfoBottomSheet(
                sheetState = sheetState,
                onConfirm = {
                    viewModel.requestExactAlarmPermission(context)
                },
                onDismiss = {

                }
            )
        }
    }
}