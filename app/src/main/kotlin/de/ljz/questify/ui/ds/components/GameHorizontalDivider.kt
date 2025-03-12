package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme

/**
 * Game-inspired horizontal divider for Questify
 * 
 * This component provides a game-themed alternative to Material 3's HorizontalDivider.
 * 
 * Features:
 * - Standard solid divider
 * - Dashed divider variant
 * - Ornate divider with decorative elements
 * - Optional label
 * - Fantasy-inspired styling
 */

@Composable
fun GameHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Dp = 1.dp
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}

@Composable
fun GameDashedHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Dp = 1.dp,
    dashLength: Float = 10f,
    gapLength: Float = 5f
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = thickness.toPx(),
            pathEffect = pathEffect,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun GameOrnateHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Dp = 1.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left line
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(thickness)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness.toPx()
            )
        }
        
        // Left ornament
        Canvas(modifier = Modifier.size(8.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width / 2
            
            // Draw diamond
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(centerX, centerY)
            )
        }
        
        // Center line
        Canvas(
            modifier = Modifier
                .width(20.dp)
                .height(thickness)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness.toPx()
            )
        }
        
        // Right ornament
        Canvas(modifier = Modifier.size(8.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width / 2
            
            // Draw diamond
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(centerX, centerY)
            )
        }
        
        // Right line
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(thickness)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness.toPx()
            )
        }
    }
}

@Composable
fun GameHorizontalDividerWithLabel(
    label: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Dp = 1.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left line
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(thickness)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness.toPx()
            )
        }
        
        // Label
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        
        // Right line
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(thickness)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness.toPx()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameHorizontalDividerPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Standard Divider",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                GameHorizontalDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Dashed Divider",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                GameDashedHorizontalDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Ornate Divider",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                GameOrnateHorizontalDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Divider with Label",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                GameHorizontalDividerWithLabel(label = "QUESTS")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameHorizontalDividerWithLabel(
                    label = "EPIC",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}