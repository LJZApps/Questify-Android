package de.ljz.questify.ui.ds.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.bannerShape
import de.ljz.questify.ui.ds.theme.shieldShape

/**
 * Game-inspired bottom navigation for Questify
 * 
 * This bottom navigation component uses shield-like items and game-inspired styling.
 * It includes:
 * - Shield-shaped container with banner-like top edge
 * - Fantasy-inspired colors
 * - Support for icons and labels
 * - Animated selection indicators
 * - Optional floating action button slot
 */

data class GameBottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val contentDescription: String? = null,
    val badgeCount: Int = 0
)

@Composable
fun GameBottomNavigation(
    items: List<GameBottomNavigationItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    containerShape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    itemShape: Shape = MaterialTheme.shapes.shieldShape(4f),
    floatingActionButton: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, containerShape)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = containerShape
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = containerShape
            )
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val selected = index == selectedItemIndex
                
                // Animate colors
                val backgroundColor by animateColorAsState(
                    targetValue = if (selected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    animationSpec = tween(durationMillis = 200),
                    label = "backgroundColor"
                )
                
                val contentColor by animateColorAsState(
                    targetValue = if (selected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
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
                
                val elevation by animateDpAsState(
                    targetValue = if (selected) 4.dp else 1.dp,
                    animationSpec = tween(durationMillis = 200),
                    label = "elevation"
                )
                
                val scale by animateFloatAsState(
                    targetValue = if (selected) 1.1f else 1f,
                    animationSpec = tween(durationMillis = 200),
                    label = "scale"
                )
                
                // Navigation item
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .shadow(elevation, itemShape)
                        .clip(itemShape)
                        .background(
                            color = backgroundColor,
                            shape = itemShape
                        )
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = itemShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Tab,
                            onClick = { onItemSelected(index) }
                        )
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icon with badge
                        Box(contentAlignment = Alignment.TopEnd) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.contentDescription,
                                tint = contentColor,
                                modifier = Modifier.size(24.dp * scale)
                            )
                            
                            // Badge
                            if (item.badgeCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.error,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.errorContainer,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (item.badgeCount > 9) "9+" else item.badgeCount.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onError,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        // Title
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                // Add space for FAB if needed
                if (floatingActionButton != null && index == items.size / 2 - 1) {
                    Spacer(modifier = Modifier.width(72.dp))
                }
            }
        }
        
        // Floating action button
        if (floatingActionButton != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                floatingActionButton()
            }
        }
    }
}

@Composable
fun GameBottomNavigationFab(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.bannerShape()
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .shadow(8.dp, shape)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameBottomNavigationPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                var selectedItemIndex by remember { mutableIntStateOf(0) }
                
                val items = listOf(
                    GameBottomNavigationItem(
                        title = "Home",
                        icon = Icons.Default.Home,
                        contentDescription = "Home"
                    ),
                    GameBottomNavigationItem(
                        title = "Profile",
                        icon = Icons.Default.Person,
                        contentDescription = "Profile"
                    ),
                    GameBottomNavigationItem(
                        title = "Settings",
                        icon = Icons.Default.Settings,
                        contentDescription = "Settings",
                        badgeCount = 5
                    )
                )
                
                Text(
                    text = "Standard Bottom Navigation",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                GameBottomNavigation(
                    items = items,
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { selectedItemIndex = it }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "With Floating Action Button",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                GameBottomNavigation(
                    items = items,
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { selectedItemIndex = it },
                    floatingActionButton = {
                        GameBottomNavigationFab(
                            icon = Icons.Default.Home,
                            contentDescription = "Add",
                            onClick = { }
                        )
                    }
                )
            }
        }
    }
}