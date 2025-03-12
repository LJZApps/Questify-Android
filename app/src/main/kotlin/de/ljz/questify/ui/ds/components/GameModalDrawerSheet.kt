package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
 * Game-inspired modal drawer sheet for Questify
 * 
 * This component provides a game-themed alternative to Material 3's ModalDrawerSheet.
 * 
 * Features:
 * - Scroll shape on the right edge
 * - Fantasy-inspired colors
 * - Decorative border
 * - Support for custom content
 */

@Composable
fun GameModalDrawerSheet(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp)
            .shadow(8.dp, shape)
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
    ) {
        // Decorative right edge
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
        )
        
        // Main content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content
        )
    }
}

@Composable
fun GameScrollDrawerSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp)
    ) {
        // Main content area with scroll shape on right side
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .shadow(8.dp, MaterialTheme.shapes.scroll)
                .clip(MaterialTheme.shapes.scroll)
                .background(
                    color = containerColor,
                    shape = MaterialTheme.shapes.scroll
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = MaterialTheme.shapes.scroll
                )
                .padding(end = 24.dp) // Extra padding on right for the scroll edge
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                content = content
            )
        }
        
        // Decorative scroll edge on the right
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(0.8f)
                .width(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(6.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameModalDrawerSheetPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            GameModalDrawerSheet {
                Text(
                    text = "Questify",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Main Menu",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Sample drawer items
                repeat(5) { index ->
                    GameDrawerItem(
                        title = "Menu Item ${index + 1}",
                        selected = index == 0,
                        onClick = { }
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScrollDrawerSheetPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            GameScrollDrawerSheet {
                Text(
                    text = "Questify",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Main Menu",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Sample drawer items
                repeat(5) { index ->
                    GameDrawerItem(
                        title = "Menu Item ${index + 1}",
                        selected = index == 0,
                        onClick = { }
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun GameDrawerItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    Color.Transparent
                }
            )
            .border(
                width = 1.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                },
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}