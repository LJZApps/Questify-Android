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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.scrollShape
import kotlinx.coroutines.launch

/**
 * Game-inspired navigation drawer for Questify
 * 
 * This drawer component uses scroll-like items and game-inspired styling.
 * It includes:
 * - Scroll-shaped items
 * - Fantasy-inspired colors
 * - Support for header with user info
 * - Animated selection indicators
 * - Support for sections with dividers
 */

data class GameDrawerItem(
    val title: String,
    val icon: ImageVector,
    val contentDescription: String? = null,
    val badgeCount: Int = 0
)

data class GameDrawerSection(
    val title: String,
    val items: List<GameDrawerItem>
)

@Composable
fun GameDrawer(
    sections: List<GameDrawerSection>,
    selectedItemIndices: Map<Int, Int>,
    onItemSelected: (sectionIndex: Int, itemIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    header: @Composable (() -> Unit)? = null,
    itemShape: Shape = MaterialTheme.shapes.scrollShape(4f),
    drawerState: DrawerState? = null
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(scrollState)
    ) {
        // Header
        if (header != null) {
            header()
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Sections
        sections.forEachIndexed { sectionIndex, section ->
            // Section title
            Text(
                text = section.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            )
            
            // Section items
            section.items.forEachIndexed { itemIndex, item ->
                val selected = selectedItemIndices[sectionIndex] == itemIndex
                
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
                
                // Drawer item
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
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
                            onClick = {
                                onItemSelected(sectionIndex, itemIndex)
                                // Close drawer if provided
                                drawerState?.let {
                                    scope.launch {
                                        it.close()
                                    }
                                }
                            }
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription,
                            tint = contentColor,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        // Title
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = contentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        
                        // Badge
                        if (item.badgeCount > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.error,
                                        shape = CircleShape
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.errorContainer,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (item.badgeCount > 99) "99+" else item.badgeCount.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onError
                                )
                            }
                        }
                    }
                }
            }
            
            // Divider between sections
            if (sectionIndex < sections.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
                GameDivider()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun GameDrawerHeader(
    username: String,
    level: Int,
    xp: Int,
    maxXp: Int,
    avatarUrl: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Username
        Text(
            text = username,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Level and XP
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            
            Spacer(modifier = Modifier.width(4.dp))
            
            Text(
                text = "Level $level",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = "$xp/$maxXp XP",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // XP Progress bar
        GameProgressBar(
            progress = xp.toFloat() / maxXp.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}