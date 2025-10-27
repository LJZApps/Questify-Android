package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.domain.repositories.SubQuestRepository
import javax.inject.Inject

class CheckSubQuestUseCase @Inject constructor(
    private val subQuestRepository: SubQuestRepository
) {

    suspend operator fun invoke(id: Int, checked: Boolean) {
        subQuestRepository.checkSubQuest(
            id = id,
            checked = checked
        )
    }

}