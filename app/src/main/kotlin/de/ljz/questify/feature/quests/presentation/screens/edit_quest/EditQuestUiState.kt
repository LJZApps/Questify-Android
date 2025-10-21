package de.ljz.questify.feature.quests.presentation.screens.edit_quest

import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.SubQuestEntity

data class EditQuestUiState(
    val title: String,
    val notes: String?,
    val difficulty: Difficulty,
    val dueDate: Long,
    val categoryId: Int?,

    val subTasks: List<SubQuestEntity>,

    val dueDateDialogVisible: Boolean,
    val addingDateTimeState: AddingDateTimeState
)
