package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme

/**
 * Game-inspired bottom app bar for Questify
 * 
 * This component provides a game-themed alternative to Material 3's BottomAppBar.
 * 
 * Features:
 * - Rounded top corners by default (can be customized)
 * - Fantasy-inspired colors
 * - Decorative border
 * - Support for floating action button
 * - Support for custom content
 */

@Composable
fun GameBottomAppBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit,
    floatingActionButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, shape)
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
            .padding(contentPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side actions
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
            
            // Floating action button (if provided)
            if (floatingActionButton != null) {
                Box(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    floatingActionButton()
                }
            }
        }
    }
}

@Composable
fun GameBottomAppBarWithFab(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit,
    fabIcon: @Composable () -> Unit,
    onFabClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    fabColor: Color = MaterialTheme.colorScheme.primary,
    fabContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
) {
    GameBottomAppBar(
        modifier = modifier,
        actions = actions,
        floatingActionButton = {
            GameFloatingActionButton(
                onClick = onFabClick,
                containerColor = fabColor,
                contentColor = fabContentColor,
                content = fabIcon
            )
        },
        containerColor = containerColor,
        contentColor = contentColor,
        borderColor = borderColor,
        tonalElevation = tonalElevation,
        contentPadding = contentPadding,
        shape = shape
    )
}

@Preview(showBackground = true)
@Composable
fun GameBottomAppBarPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Standard bottom app bar
                GameBottomAppBar(
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Bottom app bar with FAB
                GameBottomAppBarWithFab(
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    fabIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    },
                    onFabClick = { }
                )
            }
        }
    }
}