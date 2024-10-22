package de.ljz.questify.data.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.data.datastore.AppUser
import de.ljz.questify.data.shared.Points
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

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

    suspend fun addPoint(points: Points) {
        appUserDataStore.updateData {
            val updatedUser = it.copy(points = it.points + points.points)
            cachedAppUser = updatedUser // Update the cache
            updatedUser
        }
    }

}