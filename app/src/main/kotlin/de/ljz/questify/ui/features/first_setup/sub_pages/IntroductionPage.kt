package de.ljz.questify.ui.features.first_setup.sub_pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.features.first_setup.components.FeatureCard

@Composable
fun IntroductionPage() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -40 })
            ) {
                WelcomeHeader()
            }

            FeaturesList(visible)
        }
    }
}

@Composable
private fun WelcomeHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = "Willkommen bei Questify!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Verwandle deine Ziele in ein spannendes Abenteuer und stelle dich neuen Herausforderungen",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun FeaturesList(visible: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val features = listOf(
            Feature(
                icon = Icons.Default.EmojiEvents,
                title = "Persönliche Quests",
                description = "Erstelle und meistere Quests, die perfekt zu deinen Zielen passen",
                color = MaterialTheme.colorScheme.primary
            ),
            Feature(
                icon = Icons.Default.LocalActivity,
                title = "Belohnungssystem",
                description = "Sammle XP, Trophäen und spezielle Belohnungen für deine Erfolge",
                color = MaterialTheme.colorScheme.primary
            ),
            Feature(
                icon = Icons.Default.Timeline,
                title = "Fortschrittstracking",
                description = "Behalte deine Entwicklung im Blick und feiere deine Meilensteine",
                color = MaterialTheme.colorScheme.primary
            )
        )

        features.forEachIndexed { index, feature ->
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { 40 * (index + 1) }
                )
            ) {
                FeatureCard(feature)
            }
        }
    }
}

data class Feature(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val color: Color
)