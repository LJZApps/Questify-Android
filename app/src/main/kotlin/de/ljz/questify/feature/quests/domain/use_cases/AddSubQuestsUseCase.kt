package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.data.models.SubQuestEntity
import de.ljz.questify.feature.quests.domain.repositories.SubQuestRepository
import javax.inject.Inject

class AddSubQuestsUseCase @Inject constructor(
    private val subQuestRepository: SubQuestRepository
) {
    suspend operator fun invoke(subQuestEntities: List<SubQuestEntity>) {
        subQuestRepository.addSubQuests(
            subQuests = subQuestEntities
        )
    }
}