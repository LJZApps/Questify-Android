package de.ljz.questify.feature.quests.presentation.screens.create_quest

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
import de.ljz.questify.feature.quests.data.models.SubQuestEntity
import de.ljz.questify.feature.quests.data.models.descriptors.SubQuestModel
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import de.ljz.questify.feature.quests.domain.use_cases.AddQuestCategoryUseCase
import de.ljz.questify.feature.quests.domain.use_cases.AddSubQuestsUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetAllQuestCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateQuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    savedStateHandle: SavedStateHandle,

    private val addQuestCategoryUseCase: AddQuestCategoryUseCase,
    private val getAllQuestCategoriesUseCase: GetAllQuestCategoriesUseCase,

    private val addSubQuestsUseCase: AddSubQuestsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = CreateQuestUiState(
            title = "",
            description = "",
            difficulty = 0,
            isAddingReminder = false,
            selectedTime = 0,
            selectedDueDate = 0L,
            isAlertManagerInfoVisible = false,
            notificationTriggerTimes = emptyList(),
            addingDateTimeState = AddingDateTimeState.NONE,
            isDueDateInfoDialogVisible = false,
            isSelectCategoryDialogVisible = false,
            isDatePickerDialogVisible = false,
            isTimePickerDialogVisible = false,
            subTasks = emptyList()
        )
    )
    val uiState: StateFlow<CreateQuestUiState> = _uiState.asStateFlow()

    private val _questCreationSucceeded = MutableStateFlow(false)
    val questCreationSucceeded = _questCreationSucceeded.asStateFlow()

    private val createQuestRoute = savedStateHandle.toRoute<CreateQuestRoute>()
    val selectedCategoryIndex = createQuestRoute.selectedCategoryIndex

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<QuestCategoryEntity?>(null)
    val selectedCategory: StateFlow<QuestCategoryEntity?> = _selectedCategory.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                getAllQuestCategoriesUseCase.invoke()
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

    fun createQuest() {
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

            val subQuestEntities = _uiState.value.subTasks.map { subTask ->
                SubQuestEntity(
                    text = subTask.text,
                    questId = questId
                )
            }

            addSubQuestsUseCase.invoke(subQuestEntities = subQuestEntities)

            _questCreationSucceeded.update { true }
        }
    }

    fun addQuestCategory(text: String) {
        viewModelScope.launch {
            addQuestCategoryUseCase.invoke(
                questCategoryEntity = QuestCategoryEntity(
                    text = text
                )
            )
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

    fun addSubTask() {
        _uiState.update {
            it.copy(
                subTasks = _uiState.value.subTasks + SubQuestModel(text = "")
            )
        }
    }

    fun updateSubtask(index: Int, text: String) {
        _uiState.update { state ->
            state.copy(
                subTasks = state.subTasks.mapIndexed { i, subTask ->
                    if (i == index) subTask.copy(text = text) else subTask
                }
            )
        }
    }

    fun removeSubtask(index: Int) {
        _uiState.update { state ->
            state.copy(
                subTasks = state.subTasks.filterIndexed { i, _ -> i != index }
            )
        }
    }


    fun setDueDate(timestamp: Long) {
        updateUiState {
            copy(
                selectedDueDate = timestamp,
                isDatePickerDialogVisible = false,
                isTimePickerDialogVisible = false
            )
        }
    }

    fun removeDueDate() {
        updateUiState {
            copy(
                selectedDueDate = 0,
                isDatePickerDialogVisible = false,
                isTimePickerDialogVisible = false
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
    fun showDatePickerDialog() = updateUiState { copy(isDatePickerDialogVisible = true) }
    fun hideDatePickerDialog() = updateUiState { copy(isDatePickerDialogVisible = false) }
    fun showTimePickerDialog() = updateUiState { copy(isTimePickerDialogVisible = true) }
    fun hideTimePickerDialog() = updateUiState { copy(isTimePickerDialogVisible = false) }

}