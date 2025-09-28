package de.ljz.questify.feature.quests.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Badge
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.ljz.questify.core.presentation.components.tooltips.BasicPlainTooltip
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestItem(
    questWithSubQuests: QuestWithSubQuests,
    modifier: Modifier = Modifier,
    onEditButtonClicked: () -> Unit,
    onCheckButtonClicked: () -> Unit,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
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
                    text = questWithSubQuests.quest.title,
                    style = MaterialTheme.typography.titleMedium
                        .copy(
                            fontWeight = FontWeight.Bold
                        ),
                )

                questWithSubQuests.quest.notes?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                questWithSubQuests.quest.dueDate?.let {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = null
                        )

                        Text(
                            text = SimpleDateFormat(
                                "dd. MMM 'um' HH:mm",
                                Locale.getDefault()
                            ).format(it)
                        )
                    }
                }

                questWithSubQuests.subTasks.let { subTasks ->
                    if (subTasks.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Checklist,
                                contentDescription = null
                            )

                            Text(
                                text = "${subTasks.count()} Unteraufgaben"
                            )
                        }
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
                    containerColor = when (questWithSubQuests.quest.difficulty) {
                        Difficulty.EASY -> MaterialTheme.colorScheme.surfaceContainerLow
                        Difficulty.MEDIUM -> MaterialTheme.colorScheme.surfaceContainer
                        Difficulty.HARD -> MaterialTheme.colorScheme.primary
                    },
                    contentColor = when (questWithSubQuests.quest.difficulty) {
                        Difficulty.EASY -> MaterialTheme.colorScheme.onSurface
                        Difficulty.MEDIUM -> MaterialTheme.colorScheme.onSurface
                        Difficulty.HARD -> MaterialTheme.colorScheme.onPrimary
                    }
                ) {
                    Text(
                        text = when (questWithSubQuests.quest.difficulty) {
                            Difficulty.EASY -> "Leicht"
                            Difficulty.MEDIUM -> "Mittel"
                            Difficulty.HARD -> "Schwer"
                        },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            if (!questWithSubQuests.quest.done) {
                BasicPlainTooltip(
                    text = "Bearbeiten",
                    position = TooltipAnchorPosition.Above
                ) {
                    IconButton(
                        onClick = onEditButtonClicked
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    }
                }
            }

            if (questWithSubQuests.quest.done) {
                IconButton(
                    onClick = {},
                    enabled = false
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                    )
                }
            } else {
                BasicPlainTooltip(
                    text = "Fertigstellen",
                    position = TooltipAnchorPosition.Above
                ) {
                    FilledIconButton(
                        onClick = onCheckButtonClicked
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                        )
                    }
                }
            }

        }
    }
}