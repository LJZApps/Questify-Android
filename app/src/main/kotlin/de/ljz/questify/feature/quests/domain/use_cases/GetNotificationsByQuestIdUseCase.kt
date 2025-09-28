package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import javax.inject.Inject

class GetNotificationsByQuestIdUseCase @Inject constructor(
    private val questNotificationRepository: QuestNotificationRepository
) {
    suspend operator fun invoke(id: Int): List<QuestNotificationEntity> {
        return questNotificationRepository.getNotificationsByQuestId(questId = id)
    }
}