package de.ljz.questify.feature.player_stats.domain.use_cases

import de.ljz.questify.feature.player_stats.data.models.PlayerStats
import de.ljz.questify.feature.player_stats.domain.repositories.PlayerStatsRepository
import javax.inject.Inject

class UpdatePlayerStatsUseCase @Inject constructor(
    private val playerStatsRepository: PlayerStatsRepository
) {
    suspend operator fun invoke(value: PlayerStats) {
        playerStatsRepository.updatePlayerStats(newStats = value)
    }
}