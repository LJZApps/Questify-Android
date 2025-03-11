package de.ljz.questify.ui.features.first_setup.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.features.first_setup.sub_pages.Feature

@Composable
fun FeatureCard(feature: Feature) {
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.03f else 1f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    val secondaryColor = remember(feature.color) {
        feature.color.copy(alpha = 0.6f)
    }

    val gradientBrush = remember(feature.color, secondaryColor) {
        Brush.linearGradient(
            colors = listOf(feature.color, secondaryColor),
            start = Offset(0f, 0f),
            end = Offset(300f, 300f)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .graphicsLayer {
                shadowElevation = if (isHovered) 8f else 2f
                shape = RoundedCornerShape(24.dp)
            }
            .clickable { isHovered = !isHovered },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            hoveredElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Brush.radialGradient(
                        colors = listOf(
                            feature.color,
                            feature.color.copy(alpha = 0.7f)
                        )
                    ))
                    .border(2.dp, feature.color.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = feature.color,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}