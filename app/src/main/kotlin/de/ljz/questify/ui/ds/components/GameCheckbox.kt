package de.ljz.questify.ui.ds.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.shieldShape

/**
 * Game-inspired checkbox for Questify
 * 
 * This checkbox component uses a shield-like shape and game-inspired styling.
 * It includes:
 * - Shield shape by default (can be customized)
 * - Animated check mark
 * - Fantasy-inspired colors
 * - Optional label
 */

@Composable
fun GameCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.shieldShape(4f),
    label: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    // Animate scale for check mark
    val checkScale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "checkScale"
    )
    
    // Animate background color
    val backgroundColor by animateColorAsState(
        targetValue = if (checked) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        animationSpec = tween(durationMillis = 200),
        label = "backgroundColor"
    )
    
    // Determine border color based on state
    val borderColor = when {
        !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.38f)
        checked -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            role = Role.Checkbox,
            onClick = { onCheckedChange(!checked) }
        )
    ) {
        // Checkbox
        Box(
            modifier = Modifier
                .size(24.dp)
                .shadow(1.dp, shape)
                .clip(shape)
                .background(
                    color = backgroundColor,
                    shape = shape
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = shape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Check mark
            if (checkScale > 0) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(16.dp)
                        .scale(checkScale)
                )
            }
        }
        
        // Optional label
        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                }
            )
        }
    }
}

// Alternative stylized checkbox with a scroll-like checkmark
@Composable
fun GameScrollCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    // Animate rotation for scroll
    val scrollRotation by animateFloatAsState(
        targetValue = if (checked) 360f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "scrollRotation"
    )
    
    // Determine colors based on state
    val borderColor = when {
        !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.38f)
        checked -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }
    
    val fillColor = when {
        !enabled -> MaterialTheme.colorScheme.surfaceContainer
        checked -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surfaceContainer
    }
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            role = Role.Checkbox,
            onClick = { onCheckedChange(!checked) }
        )
    ) {
        // Custom scroll checkbox
        Canvas(modifier = Modifier.size(24.dp)) {
            // Draw background
            drawRoundRect(
                color = fillColor,
                cornerRadius = CornerRadius(4.dp.toPx()),
                size = Size(size.width, size.height)
            )
            
            // Draw border
            drawRoundRect(
                color = borderColor,
                cornerRadius = CornerRadius(4.dp.toPx()),
                style = Stroke(width = 2.dp.toPx()),
                size = Size(size.width, size.height)
            )
            
            if (checked) {
                // Draw scroll
                rotate(degrees = scrollRotation) {
                    // Scroll path
                    val scrollPath = Path().apply {
                        val centerX = size.width / 2
                        val centerY = size.height / 2
                        val radius = size.width / 3
                        
                        moveTo(centerX - radius, centerY)
                        cubicTo(
                            centerX - radius, centerY - radius / 2,
                            centerX + radius, centerY - radius / 2,
                            centerX + radius, centerY
                        )
                        cubicTo(
                            centerX + radius, centerY + radius / 2,
                            centerX - radius, centerY + radius / 2,
                            centerX - radius, centerY
                        )
                        close()
                    }


//                    val primaryColor = MaterialTheme.colorScheme.primary
                    
//                    drawPath(
//                        path = scrollPath,
//                        color = primaryColor,
//                        style = Stroke(
//                            width = 2.dp.toPx(),
//                            cap = StrokeCap.Round,
//                            pathEffect = PathEffect.cornerPathEffect(4.dp.toPx())
//                        )
//                    )
                    
                    // Draw lines to represent text on the scroll
//                    val lineY1 = centerY - 2.dp.toPx()
//                    val lineY2 = centerY + 2.dp.toPx()
//                    val lineStart = centerX - radius * 0.7f
//                    val lineEnd = centerX + radius * 0.7f
//
//                    drawLine(
//                        color = MaterialTheme.colorScheme.primary,
//                        start = Offset(lineStart, lineY1),
//                        end = Offset(lineEnd, lineY1),
//                        strokeWidth = 1.dp.toPx()
//                    )
//
//                    drawLine(
//                        color = MaterialTheme.colorScheme.primary,
//                        start = Offset(lineStart, lineY2),
//                        end = Offset(lineEnd, lineY2),
//                        strokeWidth = 1.dp.toPx()
//                    )
                }
            }
        }
        
        // Optional label
        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameCheckboxPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(16.dp)) {
                GameCheckbox(
                    checked = false,
                    onCheckedChange = {},
                    label = "Uncompleted Quest"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameCheckbox(
                    checked = true,
                    onCheckedChange = {},
                    label = "Completed Quest"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameCheckbox(
                    checked = false,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Disabled Quest"
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                GameScrollCheckbox(
                    checked = false,
                    onCheckedChange = {},
                    label = "Uncompleted Quest (Scroll Style)"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameScrollCheckbox(
                    checked = true,
                    onCheckedChange = {},
                    label = "Completed Quest (Scroll Style)"
                )
            }
        }
    }
}