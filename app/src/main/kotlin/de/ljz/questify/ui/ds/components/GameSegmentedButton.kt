package de.ljz.questify.ui.ds.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.shield

/**
 * Game-inspired segmented button components for Questify
 * 
 * These components provide a game-themed alternative to Material 3's SegmentedButton
 * and SingleChoiceSegmentedButtonRow.
 * 
 * They include:
 * - GameSegmentedButton: Individual button in a segmented group
 * - GameSegmentedButtonRow: Container for a row of segmented buttons
 * 
 * Features:
 * - Shield shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Animated selection indicators
 * - Support for disabled state
 */

@Composable
fun GameSegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.shield,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    // Animate colors based on selection state
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            selected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(durationMillis = 200),
        label = "backgroundColor"
    )
    
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        },
        animationSpec = tween(durationMillis = 200),
        label = "borderColor"
    )
    
    val elevation by animateDpAsState(
        targetValue = if (selected) 4.dp else 1.dp,
        animationSpec = tween(durationMillis = 200),
        label = "elevation"
    )
    
    Box(
        modifier = modifier
            .shadow(elevation, shape)
            .clip(shape)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GameSegmentedButtonRow(
    options: List<String>,
    selectedOptionIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.shield
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEachIndexed { index, option ->
            GameSegmentedButton(
                selected = index == selectedOptionIndex,
                onClick = { onOptionSelected(index) },
                enabled = enabled,
                shape = shape,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.labelMedium,
                    color = when {
                        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        index == selectedOptionIndex -> MaterialTheme.colorScheme.onPrimaryContainer
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    textAlign = TextAlign.Center
                )
            }
            
            // Add spacing between buttons, except after the last one
            if (index < options.size - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameSegmentedButtonPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                var selectedIndex by remember { mutableIntStateOf(0) }
                
                Text(
                    text = "Game Segmented Button Row",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                GameSegmentedButtonRow(
                    options = listOf("Daily", "Weekly", "Monthly"),
                    selectedOptionIndex = selectedIndex,
                    onOptionSelected = { selectedIndex = it }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Disabled State",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                GameSegmentedButtonRow(
                    options = listOf("Easy", "Medium", "Hard", "Epic"),
                    selectedOptionIndex = 2,
                    onOptionSelected = { },
                    enabled = false
                )
            }
        }
    }
}