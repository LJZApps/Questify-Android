package de.ljz.questify.core.domain.use_cases

import de.ljz.questify.core.domain.repositories.app.SortingPreferencesRepository
import de.ljz.questify.core.utils.SortingDirections
import javax.inject.Inject

class SaveQuestSortingDirectionUseCase @Inject constructor(
    private val questSortingPreferencesRepository: SortingPreferencesRepository
) {
    suspend operator fun invoke(sortingDirections: SortingDirections) {
        questSortingPreferencesRepository.saveQuestSortingDirection(sortingDirection = sortingDirections)
    }
}