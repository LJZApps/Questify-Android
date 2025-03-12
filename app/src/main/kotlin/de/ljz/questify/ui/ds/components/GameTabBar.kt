package de.ljz.questify.ui.ds.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import de.ljz.questify.ui.ds.theme.scrollShape
import de.ljz.questify.ui.ds.theme.shieldShape

/**
 * Game-inspired tab bar for Questify
 * 
 * This tab bar component uses shield-like tabs and game-inspired styling.
 * It includes:
 * - Shield-shaped tabs by default (can be customized)
 * - Fantasy-inspired colors
 * - Support for icons and labels
 * - Animated selection indicator
 */

data class GameTab(
    val title: String,
    val icon: ImageVector? = null,
    val contentDescription: String? = null
)

@Composable
fun GameTabBar(
    tabs: List<GameTab>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabShape: Shape = MaterialTheme.shapes.shieldShape(4f),
    containerShape: Shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, containerShape)
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
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = index == selectedTabIndex
                
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
                
                // Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .shadow(elevation, tabShape)
                        .clip(tabShape)
                        .background(
                            color = backgroundColor,
                            shape = tabShape
                        )
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = tabShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Tab,
                            onClick = { onTabSelected(index) }
                        )
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icon
                        if (tab.icon != null) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.contentDescription,
                                tint = contentColor,
                                modifier = Modifier.size(24.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        
                        // Title
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelMedium,
                            color = contentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GameScrollTabBar(
    tabs: List<GameTab>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabShape: Shape = MaterialTheme.shapes.scrollShape(4f),
    containerShape: Shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, containerShape)
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
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = index == selectedTabIndex
                
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
                
                // Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .shadow(elevation, tabShape)
                        .clip(tabShape)
                        .background(
                            color = backgroundColor,
                            shape = tabShape
                        )
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = tabShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Tab,
                            onClick = { onTabSelected(index) }
                        )
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icon
                        if (tab.icon != null) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.contentDescription,
                                tint = contentColor,
                                modifier = Modifier.size(24.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        
                        // Title
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelMedium,
                            color = contentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameTabBarPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(16.dp)) {
                var selectedTabIndex by remember { mutableIntStateOf(0) }
                
                val tabs = listOf(
                    GameTab(
                        title = "Home",
                        icon = Icons.Default.Home,
                        contentDescription = "Home tab"
                    ),
                    GameTab(
                        title = "Profile",
                        icon = Icons.Default.Person,
                        contentDescription = "Profile tab"
                    ),
                    GameTab(
                        title = "Settings",
                        icon = Icons.Default.Settings,
                        contentDescription = "Settings tab"
                    )
                )
                
                Text(
                    text = "Shield Style Tabs",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                GameTabBar(
                    tabs = tabs,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                var selectedScrollTabIndex by remember { mutableIntStateOf(0) }
                
                Text(
                    text = "Scroll Style Tabs",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                GameScrollTabBar(
                    tabs = tabs,
                    selectedTabIndex = selectedScrollTabIndex,
                    onTabSelected = { selectedScrollTabIndex = it }
                )
            }
        }
    }
}