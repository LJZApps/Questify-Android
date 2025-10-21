package de.ljz.questify.feature.quests.presentation.screens.create_quest

import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.feature.quests.data.models.descriptors.SubQuestModel

data class CreateQuestUiState(
    val title: String,
    val description: String,
    val difficulty: Int,
    val isAddingReminder: Boolean,
    val selectedTime: Long,
    val selectedDueDate: Long,
    val isAlertManagerInfoVisible: Boolean,
    val notificationTriggerTimes: List<Long>,
    val addingDateTimeState: AddingDateTimeState,
    val isDueDateInfoDialogVisible: Boolean,
    val isDatePickerDialogVisible: Boolean,
    val isTimePickerDialogVisible: Boolean,
    val isSelectCategoryDialogVisible: Boolean,
    val subTasks: List<SubQuestModel>
)