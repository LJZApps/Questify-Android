package de.ljz.questify.core.domain.use_cases

import de.ljz.questify.core.domain.repositories.app.SortingPreferencesRepository
import javax.inject.Inject

class UpdateShowCompletedQuestsUseCase @Inject constructor(
    private val questSortingPreferencesRepository: SortingPreferencesRepository
) {
    suspend operator fun invoke(value: Boolean) {
        questSortingPreferencesRepository.saveShowCompletedQuests(value)
    }
}