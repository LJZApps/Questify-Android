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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.scroll

/**
 * Game-inspired navigation drawer item for Questify
 * 
 * This component provides a game-themed alternative to Material 3's NavigationDrawerItem.
 * 
 * Features:
 * - Scroll shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Animated selection state
 * - Support for icons and badges
 * - Decorative border when selected
 */

@Composable
fun GameNavigationDrawerItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    badge: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.scroll,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
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
    
    val contentColor by animateColorAsState(
        targetValue = when {
            !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            selected -> MaterialTheme.colorScheme.onPrimaryContainer
            else -> MaterialTheme.colorScheme.onSurface
        },
        animationSpec = tween(durationMillis = 200),
        label = "contentColor"
    )
    
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            selected -> MaterialTheme.colorScheme.primary
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 200),
        label = "borderColor"
    )
    
    val elevation by animateDpAsState(
        targetValue = if (selected) 4.dp else if (isPressed) 2.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "elevation"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
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
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(16.dp))
            }
            
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            
            // Badge
            if (badge != null) {
                Spacer(modifier = Modifier.width(16.dp))
                badge()
            }
        }
    }
}

@Composable
fun GameNavigationDrawerItemWithBadge(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    badgeCount: Int = 0,
    shape: Shape = MaterialTheme.shapes.scroll,
    enabled: Boolean = true
) {
    if (badgeCount > 0) {
        GameNavigationDrawerItem(
            label = label,
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            icon = icon,
            badge = {
                GameBadge(
                    count = badgeCount,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            },
            shape = shape,
            enabled = enabled
        )
    } else {
        GameNavigationDrawerItem(
            label = label,
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            icon = icon,
            shape = shape,
            enabled = enabled
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameNavigationDrawerItemPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                var selectedItem by remember { mutableStateOf(0) }
                
                // Selected item
                GameNavigationDrawerItem(
                    label = "Home",
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    icon = Icons.Default.Home
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Unselected item
                GameNavigationDrawerItem(
                    label = "Profile",
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 },
                    icon = Icons.Default.Person
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Item with badge
                GameNavigationDrawerItemWithBadge(
                    label = "Settings",
                    selected = selectedItem == 2,
                    onClick = { selectedItem = 2 },
                    icon = Icons.Default.Settings,
                    badgeCount = 5
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Disabled item
                GameNavigationDrawerItem(
                    label = "Disabled Item",
                    selected = false,
                    onClick = { },
                    enabled = false
                )
            }
        }
    }
}