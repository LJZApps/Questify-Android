package de.ljz.questify.ui.features.quests.createquest.subpages

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.features.quests.createquest.CreateQuestUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailedInformationPage(
    uiState: CreateQuestUiState,
    onNotesChange: (String) -> Unit,
    onShowDueDateInfoDialog: () -> Unit,
    onShowAddingDueDateDialog: () -> Unit,
    onRemoveDueDate: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm 'Uhr'", Locale.getDefault())

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.description,
            onValueChange = { onNotesChange(it) },
            label = { Text(stringResource(R.string.create_quest_note)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            minLines = 2
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
                        text = "Fälligkeit",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier
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
                            val formattedDate = dateFormat.format(date)

                            Icon(
                                Icons.Outlined.Schedule,
                                contentDescription = null
                            )

                            Text(
                                text = if (uiState.selectedDueDate.toInt() == 0) "Keine Fälligkeit" else formattedDate,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        if (uiState.selectedDueDate > 0) {
                            Box(
                                modifier = Modifier
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
            onRemoveDueDate = {}
        )
    }
}