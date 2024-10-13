package de.ljz.questify.data.repositories

import de.ljz.questify.data.datastore.AppUser
import de.ljz.questify.data.datastore.AppUserDataStore
import kotlinx.coroutines.flow.Flow

class AppUserRepository(
  private val appUserDataStore: AppUserDataStore
) : BaseRepository() {

  fun getAppUser(): Flow<AppUser> {
    return appUserDataStore.data
  }

  suspend fun addPoint() {
    appUserDataStore.addPoint()
  }

}