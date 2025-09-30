package de.ljz.questify.feature.quests.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
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
                            painter = painterResource(R.drawable.ic_calendar_month_outlined),
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
                                painter = painterResource(R.drawable.ic_checklist),
                                contentDescription = null
                            )

                            Text(
                                text = "${subTasks.count()} Unteraufgaben"
                            )
                        }
                    }
                }

                questWithSubQuests.notifications.filter { !it.notified }.let { notificationEntities ->
                    if (notificationEntities.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_alarm_outlined),
                                contentDescription = null
                            )

                            Text(
                                text = "${notificationEntities.count()} Erinnerungen"
                            )
                        }
                    }
                }

                /*Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_timer_outlined),
                        contentDescription = null
                    )

                    Text(
                        text = "2h 30m"
                    )
                }*/

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
                            painter = painterResource(R.drawable.ic_edit_outlined),
                            contentDescription = null
                        )
                    }
                }
            }

            if (questWithSubQuests.quest.done) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )

                        Text(
                            text = "Abgeschlossen"
                        )
                    }
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
                            painter = painterResource(R.drawable.ic_check_circle_outlined),
                            contentDescription = null,
                        )
                    }
                }
            }

        }
    }
}