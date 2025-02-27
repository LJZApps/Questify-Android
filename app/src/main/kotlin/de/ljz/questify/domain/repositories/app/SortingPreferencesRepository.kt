package de.ljz.questify.domain.repositories.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.domain.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortingPreferencesRepository @Inject constructor(
    private val sortingDataStore: DataStore<Preferences>
) : BaseRepository() {

    private fun getSortKey(screenName: String) = stringPreferencesKey("sort_key_$screenName")
    private fun getSortDirection(screenName: String) = stringPreferencesKey("sort_direction_$screenName")

    suspend fun saveSortKey(screenName: String, key: String) {
        sortingDataStore.edit { preferences ->
            preferences[getSortKey(screenName)] = key
        }
    }

    suspend fun saveSortDirection(screenName: String, direction: String) {
        sortingDataStore.edit { preferences ->
            preferences[getSortDirection(screenName)] = direction
        }
    }

    fun getSortKeyFlow(screenName: String): Flow<String> =
        sortingDataStore.data.map { preferences ->
            preferences[getSortKey(screenName)] ?: "default"
        }

    fun getSortDirectionFlow(screenName: String): Flow<String> =
        sortingDataStore.data.map { preferences ->
            preferences[getSortDirection(screenName)] ?: SortingDirections.ASCENDING.name
        }
}