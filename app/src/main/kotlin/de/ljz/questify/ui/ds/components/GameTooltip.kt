package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
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
 * Game-inspired tooltips for Questify
 * 
 * These tooltip components provide game-themed alternatives to Material 3's tooltips.
 * 
 * Features:
 * - Scroll shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Decorative borders
 * - Support for plain and rich tooltip variants
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePlainTooltip(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.scroll,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    content: @Composable () -> Unit
) {
    val tooltipState = rememberTooltipState()
    
    TooltipBox(
        state = tooltipState,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            // Custom game-themed tooltip
            Box(
                modifier = Modifier
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
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
            }
        },
        modifier = modifier
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameRichTooltip(
    title: String,
    text: String,
    dismissText: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.scroll,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    actionColor: Color = MaterialTheme.colorScheme.primary,
    initialIsVisible: Boolean = false,
    isPersistent: Boolean = false,
    content: @Composable () -> Unit
) {
    val tooltipState = rememberTooltipState(
        initialIsVisible = initialIsVisible,
        isPersistent = isPersistent
    )
    
    TooltipBox(
        state = tooltipState,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            // Custom game-themed rich tooltip
            Box(
                modifier = Modifier
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
                    .padding(16.dp)
            ) {
                Column {
                    // Title
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        color = contentColor
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Text
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall,
                        color = contentColor
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Action button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
                    ) {
                        GameButton(
                            onClick = { tooltipState.dismiss() },
                            text = dismissText,
                            variant = GameButtonVariant.TERTIARY
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GameTooltipPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Plain tooltip
                GamePlainTooltip(
                    text = "This is a game-themed tooltip"
                ) {
                    GameButton(
                        onClick = { },
                        text = "Hover me"
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Rich tooltip
                GameRichTooltip(
                    title = "Quest Tip",
                    text = "Complete daily quests to earn bonus XP and unlock special rewards!",
                    dismissText = "Got it",
                    initialIsVisible = true // For preview
                ) {
                    GameButton(
                        onClick = { },
                        text = "Hover for tip"
                    )
                }
            }
        }
    }
}