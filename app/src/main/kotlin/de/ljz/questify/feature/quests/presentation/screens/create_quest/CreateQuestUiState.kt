package de.ljz.questify.feature.quests.presentation.screens.create_quest

import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.feature.quests.data.models.descriptors.SubQuestModel

data class CreateQuestUiState(
    val title: String,
    val description: String,
    val difficulty: Int,
    val isAddingReminder: Boolean, // DONE
    val selectedTime: Long,
    val selectedDueDate: Long,
    val isAlertManagerInfoVisible: Boolean, // DEPRECATED
    val notificationTriggerTimes: List<Long>,
    val addingDateTimeState: AddingDateTimeState,
    val isDueDateInfoDialogVisible: Boolean, // DEPRECATED
    val isDatePickerDialogVisible: Boolean, // DONE
    val isTimePickerDialogVisible: Boolean, // DONE
    val isSelectCategoryDialogVisible: Boolean, // DONE
    val subTasks: List<SubQuestModel>
)

sealed class DialogState {
    object None : DialogState()
    object AddReminder : DialogState()
    object SelectCategorySheet : DialogState()
    object DatePicker : DialogState()
    object TimePicker : DialogState()
}