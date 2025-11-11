package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import javax.inject.Inject

class UpsertQuestUseCase @Inject constructor(
    private val questRepository: QuestRepository
) {
    suspend operator fun invoke(quest: QuestEntity) {
        questRepository.upsertQuest(quest)
    }
}