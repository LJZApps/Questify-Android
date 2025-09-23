package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import javax.inject.Inject

class DeleteQuestCategoryUseCase @Inject constructor(
    private val questCategoryRepository: QuestCategoryRepository
) {
    suspend operator fun invoke(id: Int) {
        questCategoryRepository.deleteQuestCategory(id)
    }
}