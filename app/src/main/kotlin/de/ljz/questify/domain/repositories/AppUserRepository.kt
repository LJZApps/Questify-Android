package de.ljz.questify.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.data.shared.Points
import de.ljz.questify.domain.datastore.AppUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import okhttp3.internal.notify
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.sqrt

@Singleton
class AppUserRepository @Inject constructor(
    private val appUserDataStore: DataStore<AppUser>
) : BaseRepository() {

    private var cachedAppUser: AppUser? = null

    fun getAppUser(): Flow<AppUser> {
        return if (cachedAppUser != null) {
            flowOf(cachedAppUser!!)
        } else {
            appUserDataStore.data
                .onEach { appUser ->
                    cachedAppUser = appUser // Cache the result
                }
        }
    }

    suspend fun resetAppUserStats() {
        appUserDataStore.updateData { currentUser ->
            currentUser.copy(
                xp = 0,
                points = 0,
                level = 0
            )
        }
    }

    suspend fun addPointsAndXp(
        difficulty: Difficulty,
        halfPoints: Boolean = false,
        earnedStats: (xp: Int, points: Int, level: Int?) -> Unit
    ) {
        val baseXP = when (difficulty) {
            Difficulty.EASY -> 10
            Difficulty.MEDIUM -> 20
            Difficulty.HARD -> 25
            Difficulty.EPIC -> 40
        }
        val basePoints = when (difficulty) {
            Difficulty.EASY -> 5
            Difficulty.MEDIUM -> 10
            Difficulty.HARD -> 15
            Difficulty.EPIC -> 20
        }
        val xp = if (halfPoints) baseXP / 2 else baseXP

        appUserDataStore.updateData { currentUser ->
            val pointsMultiplier = sqrt(currentUser.level.toDouble()).coerceAtLeast(1.0)
            val points = ((if (halfPoints) basePoints / 2 else basePoints) * pointsMultiplier).toInt()

            val newTotalXP = currentUser.xp + xp

            var newLevel = 1
            while (calculateTotalXPForLevel(newLevel) <= newTotalXP) {
                newLevel++
            }
            newLevel--

            val updatedUser = currentUser.copy(xp = newTotalXP, level = newLevel, points = currentUser.points + points)
            cachedAppUser = updatedUser

            earnedStats.invoke(
                xp,
                points,
                if (newLevel == currentUser.level) null else newLevel
            )

            updatedUser
        }
    }

    private fun calculateTotalXPForLevel(level: Int): Int {
        var totalXP = 0
        for (lvl in 1..level) {
            totalXP += when {
                lvl <= 5 -> 100
                lvl <= 10 -> 150
                lvl <= 15 -> 200
                else -> 200 + ((lvl - 15) / 5) * 50
            }
        }
        return totalXP
    }

}