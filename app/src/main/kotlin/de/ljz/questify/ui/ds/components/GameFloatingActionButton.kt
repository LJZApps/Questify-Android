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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.bannerShape
import de.ljz.questify.ui.ds.theme.shield

/**
 * Game-inspired floating action button for Questify
 * 
 * This component provides a game-themed alternative to Material 3's FloatingActionButton.
 * 
 * Features:
 * - Shield or banner shape options (can be customized)
 * - Fantasy-inspired colors
 * - Animated press effect
 * - Size variants (small, regular, large)
 * - Support for custom content
 */

enum class GameFabSize {
    SMALL, REGULAR, LARGE
}

@Composable
fun GameFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    shape: Shape = MaterialTheme.shapes.shield,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    size: GameFabSize = GameFabSize.REGULAR,
    enabled: Boolean = true,
    content: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Determine size based on variant
    val fabSize: Dp = when (size) {
        GameFabSize.SMALL -> 40.dp
        GameFabSize.REGULAR -> 56.dp
        GameFabSize.LARGE -> 96.dp
    }
    
    val iconSize: Dp = when (size) {
        GameFabSize.SMALL -> 20.dp
        GameFabSize.REGULAR -> 24.dp
        GameFabSize.LARGE -> 36.dp
    }
    
    // Animate elevation change on press
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 6.dp,
        animationSpec = tween(durationMillis = 100),
        label = "elevation"
    )
    
    // Animate color change on press
    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) {
            containerColor.copy(alpha = 0.8f)
        } else {
            containerColor
        },
        animationSpec = tween(durationMillis = 100),
        label = "backgroundColor"
    )
    
    Box(
        modifier = modifier
            .size(fabSize)
            .shadow(elevation, shape)
            .clip(shape)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = contentColor.copy(alpha = 0.2f),
                shape = shape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (content != null) {
            content()
        } else if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = contentColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
fun GameCircularFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    size: GameFabSize = GameFabSize.REGULAR,
    enabled: Boolean = true,
    content: @Composable (() -> Unit)? = null
) {
    GameFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        contentDescription = contentDescription,
        shape = CircleShape,
        containerColor = containerColor,
        contentColor = contentColor,
        size = size,
        enabled = enabled,
        content = content
    )
}

@Composable
fun GameBannerFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    size: GameFabSize = GameFabSize.REGULAR,
    enabled: Boolean = true,
    content: @Composable (() -> Unit)? = null
) {
    GameFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        contentDescription = contentDescription,
        shape = MaterialTheme.shapes.bannerShape(),
        containerColor = containerColor,
        contentColor = contentColor,
        size = size,
        enabled = enabled,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun GameFloatingActionButtonPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Shield FAB (default)
                GameFloatingActionButton(
                    onClick = { },
                    icon = Icons.Default.Add,
                    contentDescription = "Add"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Circular FAB
                GameCircularFloatingActionButton(
                    onClick = { },
                    icon = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Banner FAB
                GameBannerFloatingActionButton(
                    onClick = { },
                    icon = Icons.Default.Add,
                    contentDescription = "Add",
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Small FAB
                GameFloatingActionButton(
                    onClick = { },
                    icon = Icons.Default.Add,
                    contentDescription = "Add",
                    size = GameFabSize.SMALL
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Large FAB
                GameFloatingActionButton(
                    onClick = { },
                    icon = Icons.Default.Add,
                    contentDescription = "Add",
                    size = GameFabSize.LARGE
                )
            }
        }
    }
}