package de.ljz.questify.feature.quests.presentation.screens.create_quest

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateQuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    private val questCategoryRepository: QuestCategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateQuestUiState())
    val uiState: StateFlow<CreateQuestUiState> = _uiState.asStateFlow()

    private val createQuestRoute = savedStateHandle.toRoute<CreateQuestRoute>()
    val selectedCategoryIndex = createQuestRoute.selectedCategoryIndex

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<QuestCategoryEntity?>(null)
    val selectedCategory: StateFlow<QuestCategoryEntity?> = _selectedCategory.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                questCategoryRepository.getAllQuestCategories()
                    .collectLatest { questCategoryEntities ->
                        _categories.value = questCategoryEntities

                        selectedCategoryIndex?.let { index ->
                            if (selectedCategory.value == null) {
                                _selectedCategory.value = _categories.value[index]
                            }
                        }
                    }
            }
        }
    }

    @SuppressLint("NewApi")
    fun createQuest(
        onSuccess: () -> Unit,
    ) {
        val quest = QuestEntity(
            title = _uiState.value.title,
            notes = _uiState.value.description.ifEmpty { null },
            difficulty = Difficulty.fromIndex(_uiState.value.difficulty),
            createdAt = Date(),
            dueDate = if (_uiState.value.selectedDueDate.toInt() == 0) null else Date(_uiState.value.selectedDueDate),
            categoryId = _selectedCategory.value?.id
        )

        viewModelScope.launch {
            val questId = questRepository.addMainQuest(quest)

            _uiState.value.notificationTriggerTimes.forEach { notificationTriggerTime ->
                val questNotification = QuestNotificationEntity(
                    questId = questId.toInt(),
                    notifyAt = Date(notificationTriggerTime)
                )

                questNotificationRepository.addQuestNotification(questNotification)
            }

            onSuccess.invoke()
        }
    }

    fun addQuestCategory(text: String) {
        viewModelScope.launch {
            questCategoryRepository.addQuestCategory(QuestCategoryEntity(text = text))
        }
    }

    fun selectCategory(category: QuestCategoryEntity) {
        _selectedCategory.value = category
    }

    fun removeReminder(index: Int) {
        val updatedTimes = _uiState.value.notificationTriggerTimes.toMutableList().apply {
            removeAt(index)
        }
        _uiState.value = _uiState.value.copy(notificationTriggerTimes = updatedTimes)
    }

    fun addReminder(timestamp: Long) {
        val updatedTimes = _uiState.value.notificationTriggerTimes.toMutableList().apply {
            add(timestamp)
        }
        _uiState.value = _uiState.value.copy(notificationTriggerTimes = updatedTimes)

        updateUiState {
            copy(
                isAddingReminder = true,
                addingDateTimeState = AddingDateTimeState.DATE
            )
        }
    }


    fun setDueDate(timestamp: Long) {
        updateUiState {
            copy(
                selectedDueDate = timestamp
            )
        }
    }

    fun removeDueDate() {
        updateUiState {
            copy(
                selectedDueDate = 0
            )
        }
    }

    fun updateReminderState(reminderState: AddingDateTimeState) {
        updateUiState {
            copy(addingDateTimeState = reminderState)
        }
    }

    private fun updateUiState(update: CreateQuestUiState.() -> CreateQuestUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun showCreateReminderDialog() = updateUiState {
        copy(
            isAddingReminder = true,
            addingDateTimeState = AddingDateTimeState.DATE
        )
    }

    fun hideCreateReminderDialog() = updateUiState {
        copy(
            isAddingReminder = false,
            addingDateTimeState = AddingDateTimeState.NONE
        )
    }

    fun updateTitle(title: String) = updateUiState { copy(title = title) }
    fun updateDescription(description: String) = updateUiState { copy(description = description) }
    fun updateDifficulty(difficulty: Int) = updateUiState { copy(difficulty = difficulty) }
    fun showAlertManagerInfo() = updateUiState { copy(isAlertManagerInfoVisible = true) }
    fun hideAlertManagerInfo() = updateUiState { copy(isAlertManagerInfoVisible = false) }
    fun showDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = true) }
    fun showSelectCategoryDialog() = updateUiState { copy(isSelectCategoryDialogVisible = true) }
    fun hideSelectCategoryDialog() = updateUiState { copy(isSelectCategoryDialogVisible = false) }

    fun hideDueDateInfoDialog() = updateUiState { copy(isDueDateInfoDialogVisible = false) }
    fun showAddingDueDateDialog() = updateUiState {
        copy(
            isAddingDueDate = true,
            addingDateTimeState = AddingDateTimeState.DATE
        )
    }

    fun hideAddingDueDateDialog() = updateUiState {
        copy(
            isAddingDueDate = false,
            addingDateTimeState = AddingDateTimeState.NONE
        )
    }

}