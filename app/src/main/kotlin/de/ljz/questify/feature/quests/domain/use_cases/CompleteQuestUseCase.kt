package de.ljz.questify.feature.quests.domain.use_cases

import de.ljz.questify.core.utils.calculateXpForNextLevel
import de.ljz.questify.feature.player_stats.domain.repositories.PlayerStatsRepository
import de.ljz.questify.feature.player_stats.domain.use_cases.UpdatePlayerStatsUseCase
import de.ljz.questify.feature.quests.data.models.QuestCompletionResult
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.first
import java.util.Date
import javax.inject.Inject

class CompleteQuestUseCase @Inject constructor(
    private val questRepository: QuestRepository,
    private val playerStatsRepository: PlayerStatsRepository,

    private val updatePlayerStatsUseCase: UpdatePlayerStatsUseCase
) {

    suspend operator fun invoke(quest: QuestEntity): QuestCompletionResult {
        val completionDate = Date()
        val completedQuest = quest.copy(done = true)
        questRepository.updateQuest(completedQuest)

        val currentStats = playerStatsRepository.getPlayerStats().first()
        val oldLevel = currentStats.level

        var rewardXp = quest.difficulty.xpValue
        var rewardPoints = quest.difficulty.pointsValue

        val isOverdue = quest.dueDate?.before(completionDate) ?: false
        if (isOverdue) {
            rewardXp /= 2
            rewardPoints /= 2
        }

        var newXp = currentStats.xp + rewardXp
        var newLevel = currentStats.level

        var xpForNextLevel = calculateXpForNextLevel(newLevel)
        while (newXp >= xpForNextLevel) {
            newLevel++
            newXp -= xpForNextLevel
            xpForNextLevel = calculateXpForNextLevel(newLevel)
        }

        val newStats = currentStats.copy(
            xp = newXp,
            level = newLevel,
            points = currentStats.points + rewardPoints
        )
        updatePlayerStatsUseCase.invoke(newStats)

        return QuestCompletionResult(
            earnedXp = rewardXp,
            earnedPoints = rewardPoints,
            didLevelUp = newLevel > oldLevel,
            oldLevel = oldLevel,
            newLevel = newLevel
        )
    }

}