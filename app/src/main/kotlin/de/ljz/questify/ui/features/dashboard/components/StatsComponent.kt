package de.ljz.questify.ui.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StatsComponent(
    userLevel: Int,
    userXP: Int
) {
    val currentLevelXP = calculateXPForLevel(userLevel)
    val nextLevelXP = calculateXPForLevel(userLevel + 1)

    val levelProgress = (userXP - currentLevelXP).toFloat() / (nextLevelXP - currentLevelXP).toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = { levelProgress.coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Level $userLevel", style = MaterialTheme.typography.bodyMedium)
            Text(text = "$userXP XP", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun calculateXPForLevel(level: Int): Int {
    return when {
        level <= 5 -> 100 * level
        level <= 10 -> 500 + 150 * (level - 5)
        level <= 15 -> 1250 + 200 * (level - 10)
        else -> 2250 + ((level - 15) / 5) * 50 * (level - 15)
    }
}