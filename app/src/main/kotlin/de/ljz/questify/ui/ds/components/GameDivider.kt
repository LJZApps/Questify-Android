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
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme

/**
 * Game-inspired divider for Questify
 * 
 * This divider component uses fantasy-inspired styling.
 * It includes:
 * - Standard solid divider
 * - Dashed divider variant
 * - Ornate divider with decorative elements
 * - Optional label
 */

@Composable
fun GameDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Float = 1f
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness.dp
    )
}

@Composable
fun GameDashedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Float = 1f,
    dashLength: Float = 10f,
    gapLength: Float = 5f
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = thickness,
            pathEffect = pathEffect,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun GameOrnateDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Float = 1f
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left line
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(thickness.dp)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness
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
                .height(thickness.dp)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness
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
                .height(thickness.dp)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness
            )
        }
    }
}

@Composable
fun GameDividerWithLabel(
    label: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    thickness: Float = 1f
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left line
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(thickness.dp)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness
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
                .height(thickness.dp)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = thickness
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameDividerPreview() {
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
                
                GameDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Dashed Divider",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                GameDashedDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Ornate Divider",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                GameOrnateDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Divider with Label",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                GameDividerWithLabel(label = "QUESTS")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameDividerWithLabel(
                    label = "EPIC",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}