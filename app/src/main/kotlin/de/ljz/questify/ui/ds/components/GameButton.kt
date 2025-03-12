package de.ljz.questify.ui.ds.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.shield

/**
 * Game-inspired button for Questify
 * 
 * This button component uses a shield-like shape and game-inspired styling.
 * It includes:
 * - Shield shape by default (can be customized)
 * - Animated press effect
 * - Fantasy-inspired colors
 * - Optional border for secondary buttons
 * - Different variants (primary, secondary, tertiary)
 */

enum class GameButtonVariant {
    PRIMARY, SECONDARY, TERTIARY
}

@Composable
fun GameButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: GameButtonVariant = GameButtonVariant.PRIMARY,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.shield,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate elevation change on press
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 4.dp,
        animationSpec = tween(durationMillis = 100),
        label = "elevation"
    )
    
    // Determine colors based on variant
    val (containerColor, contentColor, borderStroke) = when (variant) {
        GameButtonVariant.PRIMARY -> Triple(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            null
        )
        GameButtonVariant.SECONDARY -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        )
        GameButtonVariant.TERTIARY -> Triple(
            Color.Transparent,
            MaterialTheme.colorScheme.primary,
            null
        )
    }
    
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 88.dp, minHeight = 36.dp),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        border = borderStroke,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = { content() }
    )
}

@Composable
fun GameButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    variant: GameButtonVariant = GameButtonVariant.PRIMARY,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.shield,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    GameButton(
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (leadingIcon != null) {
                Box(modifier = Modifier.padding(end = 8.dp)) {
                    leadingIcon()
                }
            }
            
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
            
            if (trailingIcon != null) {
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    trailingIcon()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameButtonPreview() {
    GameTheme {
        GameButton(
            onClick = { },
            text = "Start Quest"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameButtonSecondaryPreview() {
    GameTheme {
        GameButton(
            onClick = { },
            text = "Cancel Quest",
            variant = GameButtonVariant.SECONDARY
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameButtonTertiaryPreview() {
    GameTheme {
        GameButton(
            onClick = { },
            text = "Skip Tutorial",
            variant = GameButtonVariant.TERTIARY
        )
    }
}