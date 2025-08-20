package de.ljz.questify.feature.profile.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.core.domain.repositories.BaseRepository
import de.ljz.questify.feature.profile.data.models.AppUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.sqrt

@Singleton
class AppUserRepository @Inject constructor(
    private val appUserDataStore: DataStore<AppUser>
) : BaseRepository() {

    fun getAppUser(): Flow<AppUser> {
        return appUserDataStore.data
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

    suspend fun saveProfile(
        displayName: String,
        aboutMe: String,
        imageUri: String
    ) {
        appUserDataStore.updateData { user ->
            user.copy(
                displayName = displayName,
                aboutMe = aboutMe,
                profilePicture = imageUri
            )
        }
    }

    suspend fun addPointsAndXp(
        difficulty: Difficulty,
        halfPoints: Boolean = false,
        earnedStats: (xp: Int, points: Int, level: Int?) -> Unit
    ) {
        val baseXP = when (difficulty) {
            Difficulty.NONE -> 0
            Difficulty.EASY -> 10
            Difficulty.MEDIUM -> 20
            Difficulty.HARD -> 25
            Difficulty.EPIC -> 40
        }
        val basePoints = when (difficulty) {
            Difficulty.NONE -> 0
            Difficulty.EASY -> 5
            Difficulty.MEDIUM -> 10
            Difficulty.HARD -> 15
            Difficulty.EPIC -> 20
        }
        val xp = if (halfPoints) baseXP / 2 else baseXP

        appUserDataStore.updateData { currentUser ->
            val pointsMultiplier = sqrt(currentUser.level.toDouble()).coerceAtLeast(1.0)
            val points =
                ((if (halfPoints) basePoints / 2 else basePoints) * pointsMultiplier).toInt()

            val newTotalXP = currentUser.xp + xp

            var newLevel = 1
            while (calculateTotalXPForLevel(newLevel) <= newTotalXP) {
                newLevel++
            }
            newLevel--

            val updatedUser = currentUser.copy(
                xp = newTotalXP,
                level = newLevel,
                points = currentUser.points + points
            )

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