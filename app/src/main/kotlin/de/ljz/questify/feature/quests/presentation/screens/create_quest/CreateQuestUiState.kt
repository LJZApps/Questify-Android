package de.ljz.questify.feature.quests.presentation.screens.create_quest

import androidx.compose.ui.graphics.vector.ImageVector
import de.ljz.questify.core.utils.AddingDateTimeState

data class CreateQuestUiState(
    val title: String = "",
    val description: String = "",
    val difficulty: Int = 0,
    val isAddingReminder: Boolean = false,
    val selectedTime: Long = 0,
    val selectedDueDate: Long = 0,
    val isAlertManagerInfoVisible: Boolean = false,
    val notificationTriggerTimes: List<Long> = listOf(),
    val addingDateTimeState: AddingDateTimeState = AddingDateTimeState.NONE,
    val isDueDateInfoDialogVisible: Boolean = false,
    val isAddingDueDate: Boolean = false
)

data class NavigationItem(
    val title: String,
    val icon: ImageVector
)