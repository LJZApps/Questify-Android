package de.ljz.questify.feature.quests.presentation.screens.edit_quest

import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.SubQuestEntity
import java.util.Date

data class EditQuestUiState(
    val title: String,
    val notes: String?,
    val difficulty: Difficulty,
    val dueDate: Date?,
    val categoryId: Int?,

    val subTasks: List<SubQuestEntity>
)
