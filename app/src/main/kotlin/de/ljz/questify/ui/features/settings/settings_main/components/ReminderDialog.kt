package de.ljz.questify.ui.features.settings.settings_main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.ljz.questify.R
import de.ljz.questify.core.application.ReminderTime
import de.ljz.questify.ui.features.settings.settings_main.ReminderItem

@Composable
fun ReminderDialog(
    selectedTime: ReminderTime,
    onConfirm: (ReminderTime) -> Unit,
    onDismiss: () -> Unit
) {
    val radioOptions = listOf(
        ReminderItem("in 5 Minuten", ReminderTime.MIN_5),
        ReminderItem("in 10 Minuten", ReminderTime.MIN_10),
        ReminderItem("in 15 Minuten", ReminderTime.MIN_15),
    )
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(radioOptions.first { it.time == selectedTime })
    }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                radioOptions.forEach { timeItem ->
                    Row(
                        Modifier.fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (timeItem == selectedOption),
                                onClick = { onOptionSelected(timeItem) },
                                role = Role.RadioButton
                            )
                            .clip(RoundedCornerShape(6.dp))
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (timeItem == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = timeItem.text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(android.R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onConfirm(selectedOption.time)
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}