package de.ljz.questify.ui.features.quests.createquest.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import de.ljz.questify.ui.features.quests.createquest.AddingReminderState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetDueDateDialog(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
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

    val combinedDateTime = remember { mutableStateOf(currentTime.timeInMillis) }

    // Update combinedDateTime whenever the date or time changes
    LaunchedEffect(
        datePickerState.selectedDateMillis
    ) {
        val selectedDate = datePickerState.selectedDateMillis
        if (selectedDate != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            combinedDateTime.value = calendar.timeInMillis
        }
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(combinedDateTime.value) }) {
                Text("Fälligkeit setzen")
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}