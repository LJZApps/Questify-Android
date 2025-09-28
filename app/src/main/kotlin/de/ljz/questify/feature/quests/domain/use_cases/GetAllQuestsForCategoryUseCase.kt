package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllQuestsForCategoryUseCase @Inject constructor(
    private val questRepository: QuestRepository
) {
    suspend operator fun invoke(id: Int): Flow<List<QuestWithSubQuests>> {
        return questRepository.getQuestsForCategoryStream(categoryId = id)
    }
}