package de.ljz.questify.ui.features.quests.create_quest.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.KeyboardHide
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.ljz.questify.R
import de.ljz.questify.core.application.AddingDateTimeState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SetDueDateDialog(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
    addingDateTimeState: AddingDateTimeState = AddingDateTimeState.NONE,
    onDateTimeStateChange: (AddingDateTimeState) -> Unit
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = currentTime.timeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(dateMillis: Long): Boolean {
                val isSelectableTime = Calendar.getInstance()

                // Setze Stunden, Minuten, Sekunden und Millisekunden auf 0, um nur das Datum zu vergleichen
                isSelectableTime.set(Calendar.HOUR_OF_DAY, 0)
                isSelectableTime.set(Calendar.MINUTE, 0)
                isSelectableTime.set(Calendar.SECOND, 0)
                isSelectableTime.set(Calendar.MILLISECOND, 0)
                return dateMillis > isSelectableTime.timeInMillis
            }
        }
    )

    val combinedDateTime = remember { mutableStateOf(currentTime.timeInMillis) }

    // Update combinedDateTime whenever the date or time changes
    LaunchedEffect(
        datePickerState.selectedDateMillis,
        timePickerState.hour,
        timePickerState.minute
    ) {
        val selectedDate = datePickerState.selectedDateMillis
        if (selectedDate != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate
            calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
            calendar.set(Calendar.MINUTE, timePickerState.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            combinedDateTime.value = calendar.timeInMillis
        }
    }

    val showTimeInput = remember { mutableStateOf(false) }

    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
    cal.set(Calendar.MINUTE, timePickerState.minute)
    cal.set(Calendar.SECOND, 0)

    when (addingDateTimeState) {
        AddingDateTimeState.NONE -> null
        AddingDateTimeState.DATE -> {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DatePickerDialog(
                    onDismissRequest = onDismiss,
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
                    ),
                    dismissButton = {
                        TextButton(
                            onClick = onDismiss,
                            shapes = ButtonDefaults.shapes()
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { onDateTimeStateChange(AddingDateTimeState.TIME) },
                            shapes = ButtonDefaults.shapes()
                        ) {
                            Text(stringResource(R.string.next))
                        }
                    },
                ) {
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    )
                }
            }
        }

        AddingDateTimeState.TIME -> {
            Dialog(
                onDismissRequest = onDismiss,
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                )
            ) {
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.create_reminder_dialog_title),
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        if (showTimeInput.value) {
                            TimeInput(timePickerState)
                        } else {
                            TimePicker(timePickerState)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row {
                                IconButton(
                                    onClick = {
                                        showTimeInput.value = !showTimeInput.value
                                    },
                                    shapes = IconButtonDefaults.shapes()
                                ) {
                                    Icon(
                                        if (showTimeInput.value) Icons.Outlined.KeyboardHide else Icons.Outlined.Keyboard,
                                        contentDescription = null
                                    )
                                }
                            }

                            Row {
                                TextButton(
                                    onClick = { onDateTimeStateChange(AddingDateTimeState.DATE) },
                                    shapes = ButtonDefaults.shapes()
                                ) {
                                    Text(stringResource(R.string.back))
                                }
                                Spacer(modifier = Modifier.width(2.dp))
                                TextButton(
                                    onClick = { onConfirm(combinedDateTime.value) },
                                    shapes = ButtonDefaults.shapes()
                                ) {
                                    Text(stringResource(R.string.create_reminder_dialog_add_reminder))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}