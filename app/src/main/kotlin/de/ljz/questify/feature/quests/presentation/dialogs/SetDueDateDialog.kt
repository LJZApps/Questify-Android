package de.ljz.questify.feature.quests.presentation.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppTextButton
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SetDueDateDialog(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
    onRemoveDueDate: () -> Unit,
    initialSelectedDateTimeMillis: Long?
) {
    val currentTime = Calendar.getInstance()
    val initialMillis = initialSelectedDateTimeMillis ?: currentTime.timeInMillis

    if (initialSelectedDateTimeMillis == null) {
        currentTime.timeInMillis = initialMillis
    }

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = initialMillis,
        selectableDates = object : SelectableDates {
            // Erlaube Auswahl ab heute (Mitternacht)
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val isSelectableTime = Calendar.getInstance()
                isSelectableTime.set(Calendar.HOUR_OF_DAY, 0)
                isSelectableTime.set(Calendar.MINUTE, 0)
                isSelectableTime.set(Calendar.SECOND, 0)
                isSelectableTime.set(Calendar.MILLISECOND, 0)
                return utcTimeMillis > isSelectableTime.timeInMillis
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            ) {
                AppTextButton(
                    onClick = onRemoveDueDate,
                ) {
                    Text("Fälligkeit entfernen")
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    AppTextButton(
                        onClick = onDismiss,
                    ) {
                        Text(stringResource(R.string.cancel))
                    }

                    AppTextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { selectedMillis ->
                                val initialCal = Calendar.getInstance().apply {
                                    timeInMillis = initialMillis
                                }

                                val selectedCal = Calendar.getInstance().apply {
                                    timeInMillis = selectedMillis

                                    set(Calendar.HOUR_OF_DAY, initialCal.get(Calendar.HOUR_OF_DAY))
                                    set(Calendar.MINUTE, initialCal.get(Calendar.MINUTE))
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }
                                onConfirm(selectedCal.timeInMillis)
                            } ?: onDismiss()
                        },
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }


}