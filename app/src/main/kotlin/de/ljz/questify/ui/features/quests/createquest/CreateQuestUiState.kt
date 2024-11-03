package de.ljz.questify.ui.features.quests.createquest

data class CreateQuestUiState(
    val title: String = "",
    val description: String = "",
    val difficulty: Int = 0,
    val isAddingReminder: Boolean = false,
    val selectedTime: Long = 0,
    val selectedDueDate: Long = 0,
    val isAlertManagerInfoVisible: Boolean = false,
    val notificationTriggerTimes: List<Long> = listOf(),
    val addingReminderState: AddingReminderState = AddingReminderState.NONE,
    val isDueDateInfoDialogVisible: Boolean = false,
    val isAddingDueDate: Boolean = false
)

enum class AddingReminderState {
    NONE,
    DATE,
    TIME;
}