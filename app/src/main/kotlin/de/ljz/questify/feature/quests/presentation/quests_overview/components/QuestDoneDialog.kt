package de.ljz.questify.feature.quests.presentation.quests_overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.ljz.questify.R
import de.ljz.questify.ui.features.quests.quests_overview.QuestDoneDialogState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestDoneDialog(
    state: QuestDoneDialogState,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaleAnim = remember { Animatable(0.3f) }
    val fadeIn = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scope.launch {
            fadeIn.animateTo(1f, animationSpec = tween(500))
            scaleAnim.animateTo(1f, animationSpec = tween(500, delayMillis = 100))
        }

        launch {
            scaleAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Check circle icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(80.dp)
                )

                Text(
                    text = stringResource(R.string.quest_done_dialog_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(fadeIn.value)
                )

                Text(
                    text = state.questName,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                if (state.xp > 0 || state.points > 0 || state.newLevel > 0) {
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)
                }

                RewardSection(
                    xp = state.xp,
                    points = state.points
                )

                AnimatedVisibility(visible = state.newLevel > 0) {
                    LevelUpBanner(newLevel = state.newLevel)
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text(
                        text = if (state.xp > 0 || state.points > 0)
                                stringResource(R.string.quest_done_dialog_take_rewards_button)
                            else
                                stringResource(R.string.quest_done_dialog_great_button)
                    )
                }
            }
        }
    }
}

@Composable
private fun RewardSection(xp: Int, points: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (xp > 0) {
            RewardItem(label = stringResource(R.string.quest_done_dialog_xp), value = "+$xp")
        }

        if (points > 0) {
            RewardItem(label = stringResource(R.string.quest_done_dialog_points), value = "+$points")
        }
    }
}

@Composable
private fun RewardItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun LevelUpBanner(newLevel: Int) {
    val scaleAnim = remember { Animatable(0.8f) }

    LaunchedEffect(newLevel) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scaleAnim.value)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = "Trending up icon",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(48.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.quest_done_dialog_level_up_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource(R.string.quest_done_dialog_level_up_description, newLevel),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
