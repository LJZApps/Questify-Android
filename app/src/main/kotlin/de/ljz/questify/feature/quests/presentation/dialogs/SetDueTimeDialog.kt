package de.ljz.questify.feature.quests.presentation.dialogs

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppTextButton
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SetDueTimeDialog(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
    onRemoveDueDate: () -> Unit,
    initialSelectedDateTimeMillis: Long?
) {
    val currentTime = Calendar.getInstance()
    if (initialSelectedDateTimeMillis != null) {
        currentTime.timeInMillis = initialSelectedDateTimeMillis
    }

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    val showTimeInput = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.create_reminder_dialog_title),
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge
                )

                if (showTimeInput.value) {
                    TimeInput(timePickerState)
                } else {
                    TimePicker(timePickerState)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppTextButton(
                        onClick = onRemoveDueDate
                    ) {
                        Text("Fälligkeit entfernen")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                showTimeInput.value = !showTimeInput.value
                            },
                        ) {
                            Icon(
                                if (showTimeInput.value)
                                    painterResource(R.drawable.ic_keyboard_hide_outlined)
                                else
                                    painterResource(R.drawable.ic_keyboard_outlined),
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.width(2.dp))

                        AppTextButton(
                            onClick = {
                                val calendar = Calendar.getInstance()

                                if (initialSelectedDateTimeMillis != null) {
                                    calendar.timeInMillis = initialSelectedDateTimeMillis
                                }

                                calendar.apply {
                                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                    set(Calendar.MINUTE, timePickerState.minute)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }

                                onConfirm(calendar.timeInMillis)
                            }
                        ) {
                            Text(stringResource(R.string.save))
                        }
                    }
                }
            }
        }
    }

}