package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.bannerShape
import kotlinx.coroutines.launch

/**
 * Game-inspired app bar for Questify
 * 
 * This app bar component uses a banner-like shape and game-inspired styling.
 * It includes:
 * - Banner shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Support for navigation icon, title, and actions
 * - Drawer integration
 * - Scroll behavior support
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    shape: Shape = MaterialTheme.shapes.bannerShape(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(4.dp, shape)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                shape = shape
            )
            .padding(contentPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation icon
            if (navigationIcon != null) {
                Box(modifier = Modifier.padding(end = 8.dp)) {
                    navigationIcon()
                }
            }
            
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            
            // Actions
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDrawerAppBar(
    title: String,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit) = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    shape: Shape = MaterialTheme.shapes.bannerShape(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
) {
    val scope = rememberCoroutineScope()
    
    GameAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open navigation drawer",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
        shape = shape,
        contentPadding = contentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBackAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit) = {},
    shape: Shape = MaterialTheme.shapes.bannerShape(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
) {
    GameAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        actions = actions,
        shape = shape,
        contentPadding = contentPadding
    )
}

@Composable
fun GameAppBarAction(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                alpha = if (enabled) 1f else 0.38f
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GameAppBarPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                GameAppBar(
                    title = "Quest Journal",
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameBackAppBar(
                    title = "Create New Quest",
                    onBackClick = {}
                )
            }
        }
    }
}