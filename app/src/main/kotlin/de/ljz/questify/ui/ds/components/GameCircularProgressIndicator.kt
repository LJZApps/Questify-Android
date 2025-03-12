package de.ljz.questify.ui.ds.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Game-inspired circular progress indicator for Questify
 * 
 * This component provides a game-themed alternative to Material 3's CircularProgressIndicator.
 * 
 * Features:
 * - Fantasy-inspired colors
 * - Decorative border
 * - Optional percentage display in center
 * - Animated progress changes
 * - Support for determinate and indeterminate states
 * - Decorative variants with glowing effects
 */

@Composable
fun GameCircularProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float? = null,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    strokeWidth: Dp = 4.dp,
    size: Dp = 48.dp,
    strokeCap: StrokeCap = StrokeCap.Round
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(2.dp, CircleShape)
            .clip(CircleShape)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (progress == null) {
            // Indeterminate progress indicator
            CircularProgressIndicator(
                modifier = Modifier.size(size - 8.dp),
                color = indicatorColor,
                trackColor = trackColor,
                strokeWidth = strokeWidth
            )
        } else {
            // Determinate progress indicator
            val animatedProgress by animateFloatAsState(
                targetValue = progress.coerceIn(0f, 1f),
                animationSpec = tween(durationMillis = 300),
                label = "progress"
            )
            
            Canvas(modifier = Modifier.size(size - 8.dp)) {
                // Draw track
                drawArc(
                    color = trackColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = strokeCap)
                )
                
                // Draw progress
                drawArc(
                    color = indicatorColor,
                    startAngle = -90f,
                    sweepAngle = 360f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = strokeCap)
                )
            }
        }
    }
}

@Composable
fun GameCircularProgressIndicatorWithPercentage(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    strokeWidth: Dp = 4.dp,
    size: Dp = 64.dp,
    strokeCap: StrokeCap = StrokeCap.Round
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(2.dp, CircleShape)
            .clip(CircleShape)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated progress
        val animatedProgress by animateFloatAsState(
            targetValue = progress.coerceIn(0f, 1f),
            animationSpec = tween(durationMillis = 300),
            label = "progress"
        )
        
        Canvas(modifier = Modifier.size(size - 8.dp)) {
            // Draw track
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = strokeCap)
            )
            
            // Draw progress
            drawArc(
                color = indicatorColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = strokeCap)
            )
        }
        
        // Percentage text
        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GameGlowingCircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    glowColor: Color = indicatorColor.copy(alpha = 0.5f),
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    strokeWidth: Dp = 4.dp,
    size: Dp = 64.dp,
    strokeCap: StrokeCap = StrokeCap.Round
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(2.dp, CircleShape)
            .clip(CircleShape)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated progress
        val animatedProgress by animateFloatAsState(
            targetValue = progress.coerceIn(0f, 1f),
            animationSpec = tween(durationMillis = 300),
            label = "progress"
        )
        
        Canvas(modifier = Modifier.size(size - 8.dp)) {
            // Draw track
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = strokeCap)
            )
            
            // Draw glow effect
            val glowWidth = strokeWidth.toPx() * 2
            drawArc(
                color = glowColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = Stroke(width = glowWidth, cap = strokeCap)
            )
            
            // Draw progress
            drawArc(
                color = indicatorColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = strokeCap)
            )
            
            // Draw decorative dots at the end of the progress arc
            if (animatedProgress > 0) {
                val angle = (-90f + 360f * animatedProgress) * (PI / 180f)
                val radius = (size.toPx() - strokeWidth.toPx() * 2) / 2
                val x = center.x + (radius * cos(angle)).toFloat()
                val y = center.y + (radius * sin(angle)).toFloat()
                
                drawCircle(
                    color = indicatorColor,
                    radius = strokeWidth.toPx() / 2,
                    center = Offset(x, y)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameCircularProgressIndicatorPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Determinate progress indicator
                GameCircularProgressIndicator(
                    progress = 0.7f
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Indeterminate progress indicator
                GameCircularProgressIndicator()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Progress indicator with percentage
                GameCircularProgressIndicatorWithPercentage(
                    progress = 0.4f
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Glowing progress indicator
                GameGlowingCircularProgressIndicator(
                    progress = 0.6f,
                    indicatorColor = MaterialTheme.colorScheme.tertiary,
                    glowColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
                )
            }
        }
    }
}