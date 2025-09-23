package de.ljz.questify.core.domain.repositories.app

import androidx.datastore.core.DataStore
import de.ljz.questify.core.data.models.descriptors.SortingPreferences
import de.ljz.questify.core.domain.repositories.BaseRepository
import de.ljz.questify.core.utils.SortingDirections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortingPreferencesRepository @Inject constructor(
    private val sortingDataStore: DataStore<SortingPreferences>
) : BaseRepository() {

    fun getQuestSortingPreferences(): Flow<QuestSortingData> {
        return sortingDataStore.data.map {
            QuestSortingData(
                questSortingDirection = it.questSortingDirection,
                showCompletedQuests = it.showCompletedQuests
            )
        }
    }

    suspend fun saveQuestSortingDirection(sortingDirection: SortingDirections) {
        sortingDataStore.updateData {
            it.copy(questSortingDirection = sortingDirection)
        }
    }

    suspend fun saveShowCompletedQuests(showCompletedQuests: Boolean) {
        sortingDataStore.updateData {
            it.copy(showCompletedQuests = showCompletedQuests)
        }
    }
}