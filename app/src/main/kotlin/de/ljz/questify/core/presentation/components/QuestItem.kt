package de.ljz.questify.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.domain.models.quests.QuestEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestItem(
    quest: QuestEntity,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape,
    difficultyIcon: @Composable (() -> Unit)? = null,
    onQuestChecked: () -> Unit,
    onQuestDelete: (id: Int) -> Unit,
    onClick: () -> Unit
) {
    var showDropdown by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                // Use combinedClickable to handle both regular clicks and long clicks
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = {
                        showDropdown = true // Show the dropdown on long click
                    }
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = shape
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 12.dp)
            ) {
                Checkbox(
                    checked = quest.done,
                    enabled = !quest.done,
                    onCheckedChange = { onQuestChecked() },
                    modifier = Modifier.padding(end = 8.dp)
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
                    // Assuming 'notes' can be null, we use a let block
                    quest.notes?.takeIf { it.isNotBlank() }?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                difficultyIcon?.let { it() }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }

        // The dropdown menu that appears on long click
        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false } // Hide when dismissed
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.delete)) }, // Assuming R.string.delete exists
                onClick = {
                    onQuestDelete(quest.id) // Trigger the delete callback
                    showDropdown = false // Hide the menu after clicking
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = null // For accessibility
                    )
                }
            )
        }
    }
}

//@UIModePreviews
//@Composable
//private fun QuestItemPreview() {
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        QuestItem(
//            quest = QuestEntity(
//                id = 0,
//                title = "Complete the epic questComplete the epic quest",
//                notes = "This is a challenging quest that requires strategy and effort. This is a challenging quest that requires strategy and effort.",
//                done = false,
//                createdAt = Date(),
//                dueDate = Date(),
//                difficulty = Difficulty.EASY
//            ),
//            difficultyIcon = {
//                EpicIcon()
//            },
//            onQuestChecked = {},
//            onQuestDelete = {},
//            onClick = {}
//        )
//
//        QuestItem(
//            quest = QuestEntity(
//                id = 0,
//                title = "Complete the epic questComplete the epic quest",
//                notes = "This is a challenging quest that requires strategy and effort. This is a challenging quest that requires strategy and effort.",
//                done = false,
//                createdAt = Date(),
//                dueDate = Date(),
//                difficulty = Difficulty.EASY
//            ),
//            difficultyIcon = {
//                MediumIcon()
//            },
//            onQuestChecked = {},
//            onQuestDelete = {},
//            questTaskCount = 0,
//            questNotificationCount = 3,
//            onClick = {}
//        )
//
//        QuestItem(
//            quest = QuestEntity(
//                id = 0,
//                title = "Complete the epic questComplete the epic quest",
//                notes = "This is a challenging quest that requires strategy and effort. This is a challenging quest that requires strategy and effort.",
//                done = false,
//                createdAt = Date(),
//                dueDate = Date(),
//                difficulty = Difficulty.EASY
//            ),
//            difficultyIcon = {
//                EpicIcon()
//            },
//            onQuestChecked = {},
//            onQuestDelete = {},
//            questTaskCount = 0,
//            questNotificationCount = 0,
//            onClick = {}
//        )
//    }
//}
//
///*
//@UIModePreviews
//@Composable
//private fun QuestItemDonePreview() {
//    QuestItem(
//        id = 1,
//        title = "Complete the epic quest",
//        description = "This is a challenging quest that requires strategy and effort.",
//        done = true,
//        dueDate = "2023-10-25",
//        difficultyIcon = {
//            EpicIcon()
//        },
//        onQuestChecked = {},
//        onClick = {}
//    )
//}
// */