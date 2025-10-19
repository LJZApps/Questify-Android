package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.data.models.SubQuestEntity
import de.ljz.questify.feature.quests.domain.repositories.SubQuestRepository
import javax.inject.Inject

class AddSubQuestUseCase @Inject constructor(
    private val subQuestRepository: SubQuestRepository
) {
    suspend operator fun invoke(subQuestEntity: SubQuestEntity) {
        subQuestRepository.addSubQuest(subQuest = subQuestEntity)
    }
}