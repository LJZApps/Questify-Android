package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.shield

/**
 * Game-inspired badge for Questify
 * 
 * This badge component uses game-inspired styling.
 * It includes:
 * - Shield or circle shape options
 * - Fantasy-inspired colors
 * - Support for text content
 * - Customizable appearance
 */

@Composable
fun GameBadge(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    borderColor: Color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
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
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GameBadge(
    count: Int,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    borderColor: Color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
    maxCount: Int = 99
) {
    GameBadge(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        borderColor = borderColor
    ) {
        Text(
            text = if (count > maxCount) "${maxCount}+" else count.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GameShieldBadge(
    count: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    maxCount: Int = 99
) {
    GameBadge(
        count = count,
        modifier = modifier,
        shape = MaterialTheme.shapes.shield,
        containerColor = containerColor,
        contentColor = contentColor,
        maxCount = maxCount
    )
}

@Composable
fun GameTextBadge(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.shield,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    borderColor: Color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
) {
    GameBadge(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        borderColor = borderColor
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameBadgePreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circle badge with number
                GameBadge(
                    count = 5
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Circle badge with large number
                GameBadge(
                    count = 999
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Shield badge
                GameShieldBadge(
                    count = 7,
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Text badge
                GameTextBadge(
                    text = "NEW",
                    containerColor = MaterialTheme.colorScheme.error
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Custom badge
                GameBadge(
                    shape = MaterialTheme.shapes.shield,
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Text(
                        text = "EPIC",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}