package de.ljz.questify.ui.ds.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.difficultyColor
import de.ljz.questify.ui.ds.theme.scroll
import de.ljz.questify.ui.ds.theme.statusColor

/**
 * Game-inspired quest card for Questify
 * 
 * This card component uses a scroll-like shape and game-inspired styling.
 * It includes:
 * - Scroll shape by default (can be customized)
 * - Animated hover and press effects
 * - Fantasy-inspired colors
 * - Visual indicators for quest difficulty and status
 * - Support for due dates and rewards
 */

@Composable
fun QuestCard(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    difficulty: Int = 0,
    isCompleted: Boolean = false,
    isActive: Boolean = true,
    dueDate: String? = null,
    reward: Int? = null,
    shape: Shape = MaterialTheme.shapes.scroll,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate elevation change on press
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 1.dp else 4.dp,
        animationSpec = tween(durationMillis = 100),
        label = "elevation"
    )
    
    // Determine status color
    val statusColor = MaterialTheme.colorScheme.statusColor(isCompleted, isActive)
    
    // Determine difficulty color
    val difficultyColor = MaterialTheme.colorScheme.difficultyColor(difficulty)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation, shape)
            .clip(shape)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with title and status
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status indicator
                Icon(
                    imageVector = if (isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                    contentDescription = if (isCompleted) "Completed" else "Not completed",
                    tint = statusColor,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Difficulty indicator
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = difficultyColor.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (difficulty) {
                        0 -> { /* No icon for difficulty 0 */ }
                        1 -> EasyIcon(tint = difficultyColor)
                        2 -> MediumIcon(tint = difficultyColor)
                        3 -> HardIcon(tint = difficultyColor)
                        4 -> EpicIcon(tint = difficultyColor)
                    }
                }
            }
            
            // Description if available
            if (description != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Footer with due date and reward
            if (dueDate != null || reward != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Due date if available
                    if (dueDate != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = "Due date",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(4.dp))
                            
                            Text(
                                text = dueDate,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    
                    // Reward if available
                    if (reward != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Reward",
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(4.dp))
                            
                            Text(
                                text = "$reward XP",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestCardPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            QuestCard(
                title = "Defeat the Dragon",
                description = "Journey to the mountain and slay the dragon that has been terrorizing the village.",
                difficulty = 4,
                dueDate = "Tomorrow",
                reward = 500,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompletedQuestCardPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            QuestCard(
                title = "Gather Herbs",
                description = "Collect healing herbs from the forest for the village healer.",
                difficulty = 1,
                isCompleted = true,
                dueDate = "Yesterday",
                reward = 100,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InactiveQuestCardPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            QuestCard(
                title = "Explore the Cave",
                description = "Venture into the mysterious cave and discover its secrets.",
                difficulty = 3,
                isActive = false,
                dueDate = "Next Week",
                reward = 300,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}