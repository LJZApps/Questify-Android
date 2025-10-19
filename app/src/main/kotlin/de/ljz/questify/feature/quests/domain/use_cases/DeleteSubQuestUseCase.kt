package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.domain.repositories.SubQuestRepository
import javax.inject.Inject

class DeleteSubQuestUseCase @Inject constructor(
    private val subQuestRepository: SubQuestRepository
) {
    suspend operator fun invoke(id: Int) {
        subQuestRepository.deleteSubQuest(id = id)
    }
}