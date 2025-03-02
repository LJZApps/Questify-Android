package de.ljz.questify.ui.features.quests.create_quest.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import de.ljz.questify.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetDueDateDialog(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()
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

    val combinedDateTime = remember { mutableLongStateOf(currentTime.timeInMillis) }

    // Update combinedDateTime whenever the date or time changes
    LaunchedEffect(
        datePickerState.selectedDateMillis,
    ) {
        val selectedDate = datePickerState.selectedDateMillis
        if (selectedDate != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            combinedDateTime.longValue = calendar.timeInMillis
        }
    }

    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)

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
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm(combinedDateTime.longValue) }) {
                    Text(stringResource(R.string.save))
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