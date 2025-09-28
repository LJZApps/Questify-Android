package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import javax.inject.Inject

class RemoveNotificationsUseCase @Inject constructor(
    private val questNotificationRepository: QuestNotificationRepository
) {
    suspend operator fun invoke(id: Int) {
        questNotificationRepository.removeNotifications(questId = id)
    }
}