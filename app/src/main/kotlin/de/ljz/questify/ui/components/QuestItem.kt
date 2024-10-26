package de.ljz.questify.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import de.ljz.questify.core.compose.UIModePreviews

@Composable
fun QuestItem(
    id: Int,
    title: String,
    description: String? = null,
    done: Boolean,
    dueDate: String? = null,
    difficultyIcon: @Composable (() -> Unit)? = null,
    onQuestChecked: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Header Row with Checkbox, Title, and Description
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = done,
                    onCheckedChange = { onQuestChecked() },
                    modifier = Modifier.padding(end = 8.dp)
                )

                // Title and Description Column
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Quest Title
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    // Description (optional, under the title)
                    description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                            maxLines = 1,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Footer with Due Date and Difficulty Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Due Date (optional)
                dueDate?.let {
                    Icon(
                        imageVector = Icons.Filled.Schedule, // Custom due date icon here
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Spacer to push difficulty icon to the end

                // Difficulty Icon if provided
                difficultyIcon?.let {
                    it()
                }
            }
        }
    }
}

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
                imageVector = Icons.Default.Star, // Example icon for difficulty
                contentDescription = "Difficulty"
            )
        },
        onQuestChecked = {}
    )
}
