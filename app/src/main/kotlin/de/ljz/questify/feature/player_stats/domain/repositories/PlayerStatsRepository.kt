package de.ljz.questify.feature.player_stats.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.feature.player_stats.data.models.PlayerStats
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerStatsRepository @Inject constructor(
    private val playerStatsStore: DataStore<PlayerStats>
) {

    fun getPlayerStats(): Flow<PlayerStats> {
        return playerStatsStore.data
    }

    suspend fun updatePlayerStats(newStats: PlayerStats) {
        playerStatsStore.updateData {
            newStats
        }
    }
}