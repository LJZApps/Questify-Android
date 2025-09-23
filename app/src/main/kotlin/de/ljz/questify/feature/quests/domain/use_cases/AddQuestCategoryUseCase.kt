package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import javax.inject.Inject

class AddQuestCategoryUseCase @Inject constructor(
    private val questCategoryRepository: QuestCategoryRepository
) {
    suspend operator fun invoke(questCategoryEntity: QuestCategoryEntity) {
        questCategoryRepository.addQuestCategory(questCategoryEntity)
    }
}