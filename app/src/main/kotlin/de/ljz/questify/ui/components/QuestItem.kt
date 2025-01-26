package de.ljz.questify.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.domain.models.quests.QuestEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QuestItem(
    quest: QuestEntity,
    difficultyIcon: @Composable (() -> Unit)? = null,
    onQuestChecked: () -> Unit,
    questTaskCount: Int = 4,
    questNotificationCount: Int = 2,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    navController: NavHostController? = null
) {
    val dueDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
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
                        textDecoration = if (quest.done) TextDecoration.LineThrough else null,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    quest.notes?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                            maxLines = 1,
                            color = Color.Gray,
                            overflow = TextOverflow.Ellipsis
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
                        imageVector = Icons.Outlined.Schedule,
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

                if (questTaskCount > 0) {
                    if (formattedDate != null) {
                        Spacer(modifier.width(8.dp))
                    }

                    Icon(
                        imageVector = Icons.Outlined.Checklist,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = questTaskCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                if (questNotificationCount > 0) {
                    if (questTaskCount > 0 || formattedDate != null) {
                        Spacer(modifier.width(8.dp))
                    }

                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = questNotificationCount.toString(),
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

@UIModePreviews
@Composable
private fun QuestItemPreview() {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        QuestItem(
            quest = QuestEntity(
                id = 0,
                title = "Complete the epic questComplete the epic quest",
                notes = "This is a challenging quest that requires strategy and effort. This is a challenging quest that requires strategy and effort.",
                done = false,
                createdAt = Date(),
                dueDate = Date(),
                difficulty = Difficulty.EASY
            ),
            onQuestChecked = {},
            onClick = {},
            questTaskCount = 7,
            difficultyIcon = {
                EpicIcon()
            }
        )

        QuestItem(
            quest = QuestEntity(
                id = 0,
                title = "Complete the epic questComplete the epic quest",
                notes = "This is a challenging quest that requires strategy and effort. This is a challenging quest that requires strategy and effort.",
                done = false,
                createdAt = Date(),
                dueDate = Date(),
                difficulty = Difficulty.EASY
            ),
            onQuestChecked = {},
            onClick = {},
            questTaskCount = 0,
            questNotificationCount = 3,
            difficultyIcon = {
                MediumIcon()
            }
        )

        QuestItem(
            quest = QuestEntity(
                id = 0,
                title = "Complete the epic questComplete the epic quest",
                notes = "This is a challenging quest that requires strategy and effort. This is a challenging quest that requires strategy and effort.",
                done = false,
                createdAt = Date(),
                dueDate = Date(),
                difficulty = Difficulty.EASY
            ),
            onQuestChecked = {},
            onClick = {},
            questTaskCount = 0,
            questNotificationCount = 0,
            difficultyIcon = {
                EpicIcon()
            }
        )
    }
}

/*
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