package de.ljz.questify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Badge
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    onQuestDelete: (id: Int) -> Unit,
    questTaskCount: Int = 0,
    questNotificationCount: Int = 0,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shadow: Dp = 1.dp,
    preview: Boolean = false
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            when (newValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onQuestDelete(quest.id)
                }

                SwipeToDismissBoxValue.StartToEnd -> {
                    onQuestChecked()
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState false
        },
        positionalThreshold = { it * .25f }
    )

    Box(modifier = modifier.padding(8.dp)) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                when (dismissState.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red, shape = RoundedCornerShape(12.dp))
                                .padding(12.dp, 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "check"
                            )
                            Spacer(modifier = Modifier)
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete"
                            )
                        }
                    }
                    SwipeToDismissBoxValue.StartToEnd -> {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Green, shape = RoundedCornerShape(12.dp))
                                .padding(12.dp, 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "check"
                            )
                            Spacer(modifier = Modifier)
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete"
                            )
                        }
                    }

                    SwipeToDismissBoxValue.Settled -> {}
                }
            },
            enableDismissFromEndToStart = !preview,
            enableDismissFromStartToEnd = !preview,
            gesturesEnabled = !preview
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = shadow),
                onClick = {
                    if (!preview) onClick() else null
                }
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
                            enabled = !preview
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
                        quest.dueDate?.let {
                            Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        if (questTaskCount > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
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
                            Spacer(modifier = Modifier.width(8.dp))
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


        if (preview) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("Preview", color = MaterialTheme.colorScheme.onPrimary)
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
            },
            onQuestDelete = {}
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
            },
            onQuestDelete = {}
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
            },
            onQuestDelete = {}
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