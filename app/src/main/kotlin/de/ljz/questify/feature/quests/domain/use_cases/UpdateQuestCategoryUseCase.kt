package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import javax.inject.Inject

class UpdateQuestCategoryUseCase @Inject constructor(
    private val questCategoryRepository: QuestCategoryRepository
) {
    suspend operator fun invoke(id: Int, value: String) {
        questCategoryRepository.updateQuestCategory(
            id = id,
            value = value
        )
    }
}