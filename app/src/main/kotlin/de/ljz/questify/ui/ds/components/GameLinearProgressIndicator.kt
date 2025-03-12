package de.ljz.questify.ui.ds.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme

/**
 * Game-inspired linear progress indicator for Questify
 * 
 * This component provides a game-themed alternative to Material 3's LinearProgressIndicator.
 * 
 * Features:
 * - Rounded corners by default (can be customized)
 * - Fantasy-inspired colors
 * - Decorative border
 * - Optional label and percentage display
 * - Animated progress changes
 * - Support for determinate and indeterminate states
 */

@Composable
fun GameLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float? = null,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    shape: Shape = RoundedCornerShape(8.dp),
    strokeCap: StrokeCap = StrokeCap.Round,
    height: Float = 8f
) {
    // Ensure progress is between 0 and 1 if provided
    val normalizedProgress = progress?.coerceIn(0f, 1f)
    
    // Animate progress changes
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress ?: 0f,
        animationSpec = tween(durationMillis = 300),
        label = "progress"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .shadow(2.dp, shape)
            .clip(shape)
            .background(
                color = trackColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
    ) {
        // If progress is null, show indeterminate indicator
        if (normalizedProgress == null) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height.dp),
                color = indicatorColor,
                trackColor = Color.Transparent
            )
        } else {
            // Show determinate indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(height.dp)
                    .clip(shape)
                    .background(
                        color = indicatorColor,
                        shape = shape
                    )
            )
        }
    }
}

@Composable
fun GameLinearProgressIndicatorWithLabel(
    progress: Float,
    modifier: Modifier = Modifier,
    label: String? = null,
    showPercentage: Boolean = false,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    shape: Shape = RoundedCornerShape(8.dp),
    strokeCap: StrokeCap = StrokeCap.Round
) {
    Column(modifier = modifier) {
        // Optional label and percentage
        if (label != null || showPercentage) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                // Label
                if (label != null) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
                
                // Percentage
                if (showPercentage) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
        
        // Progress bar
        GameLinearProgressIndicator(
            progress = progress,
            trackColor = trackColor,
            indicatorColor = indicatorColor,
            borderColor = borderColor,
            shape = shape,
            strokeCap = strokeCap
        )
    }
}

@Composable
fun GameGlowingLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    glowColor: Color = indicatorColor.copy(alpha = 0.5f),
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    shape: Shape = RoundedCornerShape(8.dp),
    height: Float = 8f
) {
    // Ensure progress is between 0 and 1
    val normalizedProgress = progress.coerceIn(0f, 1f)
    
    // Animate progress changes
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 300),
        label = "progress"
    )
    
    // Create gradient for glowing effect
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            indicatorColor,
            glowColor,
            indicatorColor
        )
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .shadow(2.dp, shape)
            .clip(shape)
            .background(
                color = trackColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
    ) {
        // Progress indicator with gradient
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .height(height.dp)
                .clip(shape)
                .background(
                    brush = gradient,
                    shape = shape
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameLinearProgressIndicatorPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Determinate progress indicator
                GameLinearProgressIndicator(
                    progress = 0.7f
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Indeterminate progress indicator
                GameLinearProgressIndicator()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Progress indicator with label and percentage
                GameLinearProgressIndicatorWithLabel(
                    progress = 0.4f,
                    label = "Loading...",
                    showPercentage = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Glowing progress indicator
                GameGlowingLinearProgressIndicator(
                    progress = 0.6f,
                    indicatorColor = MaterialTheme.colorScheme.tertiary,
                    glowColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f)
                )
            }
        }
    }
}