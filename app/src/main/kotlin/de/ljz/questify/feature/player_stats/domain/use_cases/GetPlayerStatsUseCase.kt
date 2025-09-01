package de.ljz.questify.feature.player_stats.domain.use_cases

import de.ljz.questify.feature.player_stats.data.models.PlayerStats
import de.ljz.questify.feature.player_stats.domain.repositories.PlayerStatsRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import kotlin.math.pow

data class PlayerDashboardStats(
    val playerStats: PlayerStats,
    val questsCompleted: Int,
    val xpForNextLevel: Int
)

class GetPlayerStatsUseCase @Inject constructor(
    private val playerStatsRepository: PlayerStatsRepository,
    private val questRepository: QuestRepository
) {
    private fun calculateXpForNextLevel(level: Int): Int {
        return (100.0 * 1.15.pow(level - 1)).toInt()
    }

    operator fun invoke(): Flow<PlayerDashboardStats> {
        return combine(
            playerStatsRepository.getPlayerStats(),
            questRepository.getCompletedQuestsCount()
        ) { playerStats, completedCount ->

            val xpNeeded = calculateXpForNextLevel(playerStats.level)

            PlayerDashboardStats(
                playerStats = playerStats,
                questsCompleted = completedCount,
                xpForNextLevel = xpNeeded
            )
        }
    }
}
