package de.ljz.questify.ui.features.quests.create_quest.sub_pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailedInformationPage(
    uiState: CreateQuestUiState,
    onNotesChange: (String) -> Unit,
    onShowDueDateInfoDialog: () -> Unit,
    onShowAddingDueDateDialog: () -> Unit,
    onRemoveDueDate: () -> Unit,
    onRemoveReminder: (Int) -> Unit,
    onShowCreateReminderDialog: () -> Unit
) {
    val reminderDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val dueDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.description,
            onValueChange = { onNotesChange(it) },
            label = { Text(stringResource(R.string.text_field_quest_note)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            minLines = 2,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            )
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.detailed_information_page_due_date_title),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                onShowDueDateInfoDialog()
                            }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onShowAddingDueDateDialog()
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val date = Date(uiState.selectedDueDate)
                            val formattedDate = dueDateFormat.format(date)

                            Icon(
                                Icons.Outlined.Schedule,
                                contentDescription = null
                            )

                            Text(
                                text = if (uiState.selectedDueDate.toInt() == 0) stringResource(R.string.detailed_information_page_due_date_empty) else formattedDate,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        if (uiState.selectedDueDate > 0) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { onRemoveDueDate() }
                                    .padding(0.dp)
                            ) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.detailed_information_page_reminders_title),
                    style = MaterialTheme.typography.titleMedium
                )

                uiState.notificationTriggerTimes.sortedBy { it }.forEachIndexed { index, triggerTime ->
                    val date = Date(triggerTime)
                    val formattedDate = reminderDateFormat.format(date)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                onRemoveReminder(index)
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = null
                            )

                            Text(
                                text = formattedDate,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onShowCreateReminderDialog()
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null
                        )

                        Text(
                            text = stringResource(R.string.detailed_information_page_reminders_add),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@UIModePreviews
@Composable
private fun DetailedInformationPagePreview() {
    MaterialTheme {
        DetailedInformationPage(
            uiState = CreateQuestUiState(),
            onNotesChange = {},
            onShowAddingDueDateDialog = {},
            onShowDueDateInfoDialog = {},
            onRemoveDueDate = {},
            onRemoveReminder = {},
            onShowCreateReminderDialog = {}
        )
    }
}