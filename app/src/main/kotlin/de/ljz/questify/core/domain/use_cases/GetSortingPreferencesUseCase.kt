package de.ljz.questify.core.domain.use_cases

import de.ljz.questify.core.domain.repositories.BaseRepository
import de.ljz.questify.core.domain.repositories.app.SortingPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortingPreferencesUseCase @Inject constructor(
    private val sortingPreferencesRepository: SortingPreferencesRepository
) {
    suspend operator fun invoke(): Flow<BaseRepository.QuestSortingData> {
        return sortingPreferencesRepository.getQuestSortingPreferences()
    }
}