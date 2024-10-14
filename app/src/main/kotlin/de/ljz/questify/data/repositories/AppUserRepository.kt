package de.ljz.questify.data.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.data.datastore.AppUser
import de.ljz.questify.data.shared.Points
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUserRepository @Inject constructor(
    private val appUserDataStore: DataStore<AppUser>
) : BaseRepository() {

    fun getAppUser(): Flow<AppUser> {
        return appUserDataStore.data
    }

    suspend fun addPoint(points: Points) {
        appUserDataStore.updateData {
            it.copy(
                points = it.points + points.points
            )
        }
    }

}