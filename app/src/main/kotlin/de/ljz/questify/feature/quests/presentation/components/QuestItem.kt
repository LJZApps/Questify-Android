package de.ljz.questify.feature.quests.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Badge
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.core.utils.UIModePreviews
import de.ljz.questify.feature.quests.data.models.QuestEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QuestItem(
    quest: QuestEntity,
    modifier: Modifier = Modifier,
    onQuestChecked: () -> Unit,
    onQuestDelete: (id: Int) -> Unit,
    questTaskCount: Int = 0,
    questNotificationCount: Int = 0,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
//        onClick = { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
           Column(
               verticalArrangement = Arrangement.spacedBy(8.dp),
               modifier = Modifier.weight(1f)
           ) {
               Text(
                   text = quest.title,
                   style = MaterialTheme.typography.titleMedium
                       .copy(
                           fontWeight = FontWeight.Bold
                       ),
               )

               quest.notes?.let {
                   Text(
                       text = it,
                       style = MaterialTheme.typography.bodyMedium,
                       maxLines = 2,
                       overflow = TextOverflow.Ellipsis
                   )
               }

               quest.dueDate?.let {
                   Row(
                       horizontalArrangement = Arrangement.spacedBy(8.dp),
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Icon(
                           imageVector = Icons.Outlined.CalendarMonth,
                           contentDescription = null
                       )

                       Text(
                           text = SimpleDateFormat("dd. MMM 'um' HH:mm", Locale.getDefault()).format(it)
                       )
                   }
               }

               Row(
                   horizontalArrangement = Arrangement.spacedBy(8.dp),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(
                       imageVector = Icons.Outlined.Alarm,
                       contentDescription = null
                   )

                   Text(
                       text = "2 Erinnerungen"
                   )
               }

               Row(
                   horizontalArrangement = Arrangement.spacedBy(8.dp),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(
                       imageVector = Icons.Outlined.Timer,
                       contentDescription = null
                   )

                   Text(
                       text = "2h 30m"
                   )
               }

               Badge(
                   containerColor = when (quest.difficulty) {
                       Difficulty.EASY -> MaterialTheme.colorScheme.surfaceContainerLow
                       Difficulty.MEDIUM -> MaterialTheme.colorScheme.surfaceContainer
                       Difficulty.HARD -> MaterialTheme.colorScheme.primary
                   },
                   contentColor = when (quest.difficulty) {
                       Difficulty.EASY -> MaterialTheme.colorScheme.onSurface
                       Difficulty.MEDIUM -> MaterialTheme.colorScheme.onSurface
                       Difficulty.HARD -> MaterialTheme.colorScheme.onPrimary
                   }
               ) {
                   Text(
                       text = when (quest.difficulty) {
                           Difficulty.EASY -> "Leicht"
                           Difficulty.MEDIUM -> "Mittel"
                           Difficulty.HARD -> "Schwer"
                       },
                       modifier = Modifier.padding(4.dp)
                   )
               }
           }


            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null
                )
            }

            FilledIconButton(
                onClick = onQuestChecked
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                )
            }
        }
    }
}

@UIModePreviews
@Composable
private fun QuestItemPreview() {
    Column(
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
            onQuestDelete = {},
            questTaskCount = 7,
            onClick = {}
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
            onQuestDelete = {},
            questTaskCount = 0,
            questNotificationCount = 3,
            onClick = {}
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
            onQuestDelete = {},
            questTaskCount = 0,
            questNotificationCount = 0,
            onClick = {}
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