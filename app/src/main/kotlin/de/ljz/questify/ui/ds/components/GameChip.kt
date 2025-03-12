package de.ljz.questify.ui.ds.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.shield

/**
 * Game-inspired chip components for Questify
 * 
 * These chip components use shield-like shapes and game-inspired styling.
 * They include:
 * - GameChip: Basic chip for displaying information
 * - GameFilterChip: Selectable chip for filtering
 * 
 * All chips support:
 * - Shield shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Click handling with animated feedback
 * - Optional leading and trailing icons
 */

@Composable
fun GameChip(
    label: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.shield,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate elevation change on press
    val elevation by animateDpAsState(
        targetValue = if (isPressed && onClick != null) 1.dp else 2.dp,
        animationSpec = tween(durationMillis = 100),
        label = "elevation"
    )
    
    Box(
        modifier = modifier
            .shadow(elevation, shape)
            .clip(shape)
            .background(
                color = containerColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = shape
            )
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
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading icon
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = contentColor
            )
            
            // Trailing icon
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun GameFilterChip(
    label: String,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.shield,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate colors based on selection state
    val containerColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        animationSpec = tween(durationMillis = 200),
        label = "containerColor"
    )
    
    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        animationSpec = tween(durationMillis = 200),
        label = "contentColor"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        },
        animationSpec = tween(durationMillis = 200),
        label = "borderColor"
    )
    
    // Animate elevation change on press
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 1.dp else if (selected) 3.dp else 2.dp,
        animationSpec = tween(durationMillis = 100),
        label = "elevation"
    )
    
    Box(
        modifier = modifier
            .shadow(elevation, shape)
            .clip(shape)
            .background(
                color = containerColor,
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
                onClick = { onSelectedChange(!selected) }
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Selection indicator
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameChipPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                GameChip(
                    label = "Basic Chip"
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                GameChip(
                    label = "Clickable Chip",
                    onClick = { }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                GameChip(
                    label = "With Icons",
                    leadingIcon = Icons.Default.Check,
                    trailingIcon = Icons.Default.Close,
                    onClick = { }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                var selected by remember { mutableStateOf(false) }
                
                GameFilterChip(
                    label = "Filter Chip",
                    selected = selected,
                    onSelectedChange = { selected = it }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                GameFilterChip(
                    label = "Selected Filter",
                    selected = true,
                    onSelectedChange = { }
                )
            }
        }
    }
}