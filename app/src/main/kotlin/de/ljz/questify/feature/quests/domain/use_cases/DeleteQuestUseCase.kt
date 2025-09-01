package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import javax.inject.Inject

class DeleteQuestUseCase @Inject constructor(
    private val questRepository: QuestRepository
) {
    suspend operator fun invoke(questId: Int) {
        questRepository.deleteQuest(questId)
    }
}