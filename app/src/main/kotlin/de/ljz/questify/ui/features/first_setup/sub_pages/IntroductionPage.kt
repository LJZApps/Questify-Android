package de.ljz.questify.ui.features.first_setup.sub_pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.ui.features.first_setup.components.FeatureCard

@Composable
fun IntroductionPage(
    visible: Boolean
) {
    // Dekorative Hintergrundformen
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Hintergrund-Blobs
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset((-80).dp, (-100).dp)
                .blur(70.dp)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(200.dp, 400.dp)
                .blur(60.dp)
                .background(
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                    CircleShape
                )
        )

        // Hauptinhalt
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000)) + slideInVertically(
                    initialOffsetY = { -80 },
                    animationSpec = tween(1200)
                )
            ) {
                WelcomeHeader()
            }

            Spacer(modifier = Modifier.height(16.dp))

            FeaturesList(visible)
        }
    }
}

@Composable
private fun WelcomeHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(16.dp, CircleShape)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
        }

        Text(
            text = stringResource(R.string.introduction_page_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = stringResource(R.string.introduction_page_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
private fun FeaturesList(visible: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val features = listOf(
            Feature(
                icon = Icons.Default.EmojiEvents,
                title = stringResource(R.string.introduction_page_personal_quests_title),
                description = stringResource(R.string.introduction_page_personal_quests_description),
                color = MaterialTheme.colorScheme.primary
            ),
            Feature(
                icon = Icons.Default.LocalActivity,
                title = stringResource(R.string.introduction_page_reward_system_title),
                description = stringResource(R.string.introduction_page_reward_system_description),
                color = MaterialTheme.colorScheme.secondary
            ),
            Feature(
                icon = Icons.Default.Timeline,
                title = stringResource(R.string.introduction_page_progess_tracking_title),
                description = stringResource(R.string.introduction_page_progess_tracking_description),
                color = MaterialTheme.colorScheme.tertiary
            )
        )

        features.forEachIndexed { index, feature ->
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000)) + slideInVertically(
                    initialOffsetY = { 100 * (index + 1) },
                    animationSpec = tween(800 + (index * 200))
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