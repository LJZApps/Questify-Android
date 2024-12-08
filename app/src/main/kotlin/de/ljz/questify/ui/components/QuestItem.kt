package de.ljz.questify.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import de.ljz.questify.domain.models.quests.QuestEntity
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun QuestItem(
    quest: QuestEntity,
    difficultyIcon: @Composable (() -> Unit)? = null,
    onQuestChecked: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    navController: NavHostController? = null
) {
    val dueDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm 'Uhr'", Locale.getDefault())
    val formattedDate = quest.dueDate?.let { dueDateFormat.format(it) }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = quest.done,
                    onCheckedChange = {
                        onQuestChecked()
                    },
                    modifier = Modifier.padding(end = 8.dp),
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = quest.title,
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = if (quest.done) TextDecoration.LineThrough else null
                    )
                    quest.notes?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                            maxLines = 1,
                            color = Color.Gray
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                formattedDate?.let {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                difficultyIcon?.let { it() }
            }
        }
    }
}

/*
@UIModePreviews
@Composable
private fun QuestItemPreview() {
    QuestItem(
        id = 1,
        title = "Complete the epic quest",
        description = "This is a challenging quest that requires strategy and effort.",
        done = false,
        dueDate = "2023-10-25",
        difficultyIcon = {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Difficulty"
            )
        },
        onQuestChecked = {},
        onClick = {}
    )
}

@UIModePreviews
@Composable
private fun QuestItemDonePreview() {
    QuestItem(
        id = 1,
        title = "Complete the epic quest",
        description = "This is a challenging quest that requires strategy and effort.",
        done = true,
        dueDate = "2023-10-25",
        difficultyIcon = {
            EpicIcon()
        },
        onQuestChecked = {},
        onClick = {}
    )
}
 */