package de.ljz.questify.core.domain.repositories.app

import androidx.datastore.core.DataStore
import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.core.domain.datastore.SortingPreferences
import de.ljz.questify.core.domain.repositories.BaseRepository
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
                questSorting = it.questSorting,
                questSortingDirection = it.questSortingDirection,
                showCompletedQuests = it.showCompletedQuests
            )
        }
    }

    suspend fun saveQuestSorting(questSorting: QuestSorting) {
        sortingDataStore.updateData {
            it.copy(questSorting = questSorting)
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