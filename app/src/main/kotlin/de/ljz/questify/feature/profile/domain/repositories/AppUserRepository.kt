package de.ljz.questify.feature.profile.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.core.domain.repositories.BaseRepository
import de.ljz.questify.feature.profile.data.models.AppUser
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
}