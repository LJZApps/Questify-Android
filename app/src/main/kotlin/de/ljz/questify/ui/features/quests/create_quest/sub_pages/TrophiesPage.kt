package de.ljz.questify.ui.features.quests.create_quest.sub_pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TrophiesPage(
    uiState: CreateQuestUiState,
    onRemoveReminder: (Int) -> Unit,
    onShowCreateReminderDialog: () -> Unit
) {
    /*Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
                    text = "Erinnerungen",
                    style = MaterialTheme.typography.titleMedium
                )

                uiState.notificationTriggerTimes.sortedBy { it }.forEachIndexed { index, triggerTime ->
                    val date = Date(triggerTime)
                    val formattedDate = dateFormat.format(date)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
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
                            text = "Erinnerung hinzufügen",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }*/
}

@UIModePreviews
@Composable
private fun ReminderPagePreview() {
    MaterialTheme {
        TrophiesPage(
            uiState = CreateQuestUiState(
                notificationTriggerTimes = listOf(1734963333)
            ),
            onRemoveReminder = {  },
            onShowCreateReminderDialog = {  }
        )
    }
}