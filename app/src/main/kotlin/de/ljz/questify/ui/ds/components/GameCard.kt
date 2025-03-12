package de.ljz.questify.ui.ds.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.scroll

/**
 * Game-inspired card components for Questify
 * 
 * These card components use scroll-like shapes and game-inspired styling.
 * They include:
 * - GameCard: Basic card with optional border
 * - GameElevatedCard: Card with elevation and shadow
 * - GameOutlinedCard: Card with a prominent border
 * 
 * All cards support:
 * - Scroll shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Click handling with animated feedback
 * - Customizable content
 */

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.scroll,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    border: BorderStroke? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate elevation change on press
    val elevation by animateDpAsState(
        targetValue = if (isPressed && onClick != null) 1.dp else 0.dp,
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
            .then(
                if (border != null) {
                    Modifier.border(
                        border = border,
                        shape = shape
                    )
                } else {
                    Modifier
                }
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
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun GameElevatedCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.scroll,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    elevation: Float = 4f,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate elevation change on press
    val animatedElevation by animateDpAsState(
        targetValue = if (isPressed && onClick != null) 1.dp else elevation.dp,
        animationSpec = tween(durationMillis = 100),
        label = "elevation"
    )
    
    Box(
        modifier = modifier
            .shadow(animatedElevation, shape)
            .clip(shape)
            .background(
                color = containerColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
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
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun GameOutlinedCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.scroll,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    borderWidth: Float = 2f,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animate elevation change on press
    val elevation by animateDpAsState(
        targetValue = if (isPressed && onClick != null) 1.dp else 0.dp,
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
                width = borderWidth.dp,
                color = borderColor,
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
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameCardPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                GameCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Basic Game Card",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                
                Box(modifier = Modifier.padding(vertical = 16.dp)) {
                    GameElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }
                    ) {
                        Text(
                            text = "Elevated Game Card (Clickable)",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                
                GameOutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Outlined Game Card",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}