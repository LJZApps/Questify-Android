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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme

/**
 * Game-inspired progress bar for Questify
 * 
 * This progress bar component uses a fantasy-inspired design.
 * It includes:
 * - Animated progress indicator
 * - Fantasy-inspired colors
 * - Optional label and percentage display
 * - Customizable track and indicator colors
 */

@Composable
fun GameProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(8.dp),
    showPercentage: Boolean = false,
    label: String? = null
) {
    // Ensure progress is between 0 and 1
    val normalizedProgress = progress.coerceIn(0f, 1f)
    
    // Animate progress changes
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 300),
        label = "progress"
    )
    
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
                        text = "${(normalizedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
        
        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(shape)
                .background(trackColor)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    shape = shape
                )
        ) {
            // Progress indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(16.dp)
                    .clip(shape)
                    .background(indicatorColor)
            )
        }
    }
}

@Composable
fun GameProgressBarWithGlowingEdge(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    glowColor: Color = indicatorColor.copy(alpha = 0.5f),
    shape: Shape = RoundedCornerShape(8.dp),
    showPercentage: Boolean = false,
    label: String? = null
) {
    // Ensure progress is between 0 and 1
    val normalizedProgress = progress.coerceIn(0f, 1f)
    
    // Animate progress changes
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 300),
        label = "progress"
    )
    
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
                        text = "${(normalizedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
        
        // Progress bar with glowing edge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(shape)
                .background(trackColor)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    shape = shape
                )
        ) {
            // Progress indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(16.dp)
                    .clip(shape)
                    .background(indicatorColor)
            )
            
            // Glowing edge (only visible if progress > 0)
            if (animatedProgress > 0) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(16.dp)
                        .align(Alignment.CenterStart)
                        .clip(shape)
                        .background(glowColor)
//                        .offset(
//                            x = (animatedProgress * (modifier.fillMaxWidth().width().value - 4)).dp
//                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameProgressBarPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                GameProgressBar(
                    progress = 0.75f,
                    label = "Experience",
                    showPercentage = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameProgressBar(
                    progress = 0.3f,
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameProgressBarWithGlowingEdge(
                    progress = 0.6f,
                    label = "Health",
                    showPercentage = true,
                    indicatorColor = Color(0xFFE57373), // Red
                    glowColor = Color(0xFFEF9A9A).copy(alpha = 0.7f) // Lighter red
                )
            }
        }
    }
}