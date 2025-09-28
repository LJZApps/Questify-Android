package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllQuestCategoriesUseCase @Inject constructor(
    private val questCategoryRepository: QuestCategoryRepository
) {
    suspend operator fun invoke(): Flow<List<QuestCategoryEntity>> {
        return questCategoryRepository.getAllQuestCategories()
    }
}