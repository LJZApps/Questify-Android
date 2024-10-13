package de.ljz.questify.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import de.ljz.questify.data.shared.Points
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import java.io.File

class AppUserDataStore(
  private val context: Context
) {
  private val dataStore: DataStore<AppUser> = DataStoreFactory.create(
    serializer = AppUserSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler { AppUser() },
    migrations = listOf(),
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    produceFile = { File(context.filesDir, "datastore/app_user.json") }
  )

  val data: Flow<AppUser> = dataStore.data

  suspend fun addPoint(points: Points) {
    dataStore.updateData { currentUser ->
      currentUser.copy(points = currentUser.points + points.points)
    }
  }
}