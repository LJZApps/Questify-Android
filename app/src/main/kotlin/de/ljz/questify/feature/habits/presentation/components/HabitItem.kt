package de.ljz.questify.feature.habits.presentation.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.feature.habits.data.models.HabitType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    title: String,
    notes: String? = null,
    types: List<HabitType>,
    positiveCounter: Int,
    negativeCounter: Int,
    shape: Shape = RoundedCornerShape(4.dp),
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onClick: () -> Unit
) {
    // Animierte Zähler für eine flüssige Darstellung
    val animatedPositiveCounter by animateIntAsState(
        targetValue = positiveCounter,
        animationSpec = tween(300), label = "positiveCounterAnimation"
    )
    val animatedNegativeCounter by animateIntAsState(
        targetValue = negativeCounter,
        animationSpec = tween(300), label = "negativeCounterAnimation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (!notes.isNullOrBlank()) {
                    Text(
                        text = notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (types.contains(HabitType.NEGATIVE)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        FilledIconButton(
                            onClick = onDecrease,
                            shape = CircleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Negative Aktion")
                        }
                        Spacer(Modifier.height(4.dp))
                        Badge(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ) { Text(animatedNegativeCounter.toString()) }
                    }
                }

                if (types.contains(HabitType.POSITIVE)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        FilledIconButton(
                            onClick = onIncrease,
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Positive Aktion")
                        }
                        Spacer(Modifier.height(4.dp))
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ) { Text(animatedPositiveCounter.toString()) }
                    }
                }
            }
        }
    }
}


// --- PREVIEW FUNKTIONEN ---

@Preview(showBackground = true, name = "Positive Habit")
@Composable
private fun HabitItemPositivePreview() {
    MaterialTheme {
        HabitItem(
            title = "Täglich Sport machen",
            notes = "Mindestens 30 Minuten",
            types = listOf(HabitType.POSITIVE),
            positiveCounter = 5,
            negativeCounter = 0,
            onIncrease = {},
            onDecrease = {},
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Negative Habit")
@Composable
private fun HabitItemNegativePreview() {
    MaterialTheme {
        HabitItem(
            title = "Nicht Rauchen",
            types = listOf(HabitType.NEGATIVE),
            positiveCounter = 0,
            negativeCounter = 2,
            onIncrease = {},
            onDecrease = {},
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Both Types Habit")
@Composable
private fun HabitItemBothPreview() {
    MaterialTheme {
        HabitItem(
            title = "Zuckerkonsum",
            notes = "Belohnung für 'nein', Strafe für 'ja'",
            types = listOf(HabitType.POSITIVE, HabitType.NEGATIVE),
            positiveCounter = 3,
            negativeCounter = 1,
            onIncrease = {},
            onDecrease = {},
            onClick = {}
        )
    }
}
