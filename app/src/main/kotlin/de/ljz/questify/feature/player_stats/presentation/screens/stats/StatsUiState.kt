package de.ljz.questify.feature.player_stats.presentation.screens.stats

import de.ljz.questify.feature.player_stats.data.models.PlayerStats

data class StatsUiState(
    val isLoading: Boolean = true,

    val playerStats: PlayerStats = PlayerStats(),

    val questsCompleted: Int = 0,

    val xpForNextLevel: Int = 100
)