package de.ljz.questify.feature.player_stats.presentation.screens.stats

import de.ljz.questify.feature.player_stats.data.models.PlayerStats

data class StatsUiState(
    val playerStats: PlayerStats,
    val questsCompleted: Int,
    val xpForNextLevel: Int
)