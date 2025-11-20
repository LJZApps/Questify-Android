package de.ljz.questify.feature.quests.presentation.screens.edit_quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.models.SubQuestEntity
import de.ljz.questify.feature.quests.domain.use_cases.AddQuestCategoryUseCase
import de.ljz.questify.feature.quests.domain.use_cases.AddSubQuestsUseCase
import de.ljz.questify.feature.quests.domain.use_cases.CancelQuestNotificationsUseCase
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.DeleteSubQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetAllQuestCategoriesUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetQuestByIdAsFlowUseCase
import de.ljz.questify.feature.quests.domain.use_cases.GetQuestByIdUseCase
import de.ljz.questify.feature.quests.domain.use_cases.UpsertQuestUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel(assistedFactory = EditQuestViewModel.Factory::class)
class EditQuestViewModel @AssistedInject constructor(
    @Assisted private val id: Int,

    private val upsertQuestUseCase: UpsertQuestUseCase,
    private val getQuestByIdUseCase: GetQuestByIdUseCase,
    private val getQuestByIdAsFlowUseCase: GetQuestByIdAsFlowUseCase,
    private val deleteQuestUseCase: DeleteQuestUseCase,

    private val addQuestCategoryUseCase: AddQuestCategoryUseCase,
    private val getAllQuestCategoriesUseCase: GetAllQuestCategoriesUseCase,

    private val addSubQuestsUseCase: AddSubQuestsUseCase,
    private val deleteSubQuestUseCase: DeleteSubQuestUseCase,

    private val cancelQuestNotificationsUseCase: CancelQuestNotificationsUseCase,
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): EditQuestViewModel
    }

    private val _uiState = MutableStateFlow(
        value = EditQuestUiState(
            title = "",
            notes = "",
            difficulty = 0,
            dueDate = 0,
            categoryId = null,

            notificationTriggerTimes = emptyList(),
            subTasks = emptyList(),

            addingDateTimeState = AddingDateTimeState.DATE,
            dialogState = DialogState.None,
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _uiEffects = Channel<EditQuestUiEffect>()
    val uiEffects = _uiEffects.receiveAsFlow()

    private var _copiedQuestEntity: QuestEntity? = null

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
                    }
            }

            launch {
                getQuestByIdUseCase.invoke(id).let { questWithSubQuests ->
                    val notifications = questWithSubQuests.notifications
                        .filter { !it.notified }
                        .map { it.notifyAt.time }

                    questWithSubQuests.quest.let { questEntity ->
                        _copiedQuestEntity = questEntity

                        _uiState.update {
                            it.copy(
                                title = questEntity.title,
                                notes = questEntity.notes ?: "",
                                difficulty = questEntity.difficulty.ordinal,
                                dueDate = questEntity.dueDate?.toInstant()?.toEpochMilli()?: 0L,
                                categoryId = questEntity.categoryId,
                                notificationTriggerTimes = notifications
                            )
                        }
                    }
                }
            }

            launch {
                getQuestByIdAsFlowUseCase.invoke(id).let { questWithSubQuestsFlow ->
                    questWithSubQuestsFlow.collectLatest { questWithSubQuests ->
                        questWithSubQuests?.subTasks.let { subQuestEntities ->
                            _uiState.update {
                                it.copy(subTasks = subQuestEntities?: emptyList())
                            }
                        }
                    }
                }
            }

        }
    }

    fun onUiEvent(event: EditQuestUiEvent) {
        when (event) {
            is EditQuestUiEvent.OnSaveQuest -> {
                viewModelScope.launch {
                    launch {
                        _copiedQuestEntity?.let { copiedQuestEntity ->
                            val updatedQuestEntity = copiedQuestEntity.copy(
                                title = _uiState.value.title,
                                notes = _uiState.value.notes,
                                difficulty = Difficulty.fromIndex(_uiState.value.difficulty),
                                dueDate = if (_uiState.value.dueDate.toInt() == 0) null else Date(
                                    _uiState.value.dueDate
                                ),
                                categoryId = _selectedCategory.value?.id
                            )

                            upsertQuestUseCase.invoke(updatedQuestEntity)

                            addSubQuestsUseCase.invoke(_uiState.value.subTasks)

                            _uiEffects.send(EditQuestUiEffect.OnNavigateUp)
                        }
                    }
                }
            }

            is EditQuestUiEvent.OnDeleteQuest -> {
                viewModelScope.launch {
                    cancelQuestNotificationsUseCase.invoke(id = id)

                    deleteQuestUseCase.invoke(questId = id)

                    _uiEffects.send(EditQuestUiEffect.OnNavigateUp)
                }
            }

            is EditQuestUiEvent.OnTitleUpdated -> {
                _uiState.update {
                    it.copy(title = event.value)
                }
            }

            is EditQuestUiEvent.OnDescriptionUpdated -> {
                _uiState.update {
                    it.copy(notes = event.value)
                }
            }

            is EditQuestUiEvent.OnDifficultyUpdated -> {
                _uiState.update {
                    it.copy(difficulty = event.value)
                }
            }

            is EditQuestUiEvent.OnShowDialog -> {
                _uiState.update {
                    it.copy(dialogState = event.dialogState)
                }
            }

            is EditQuestUiEvent.OnCloseDialog -> {
                _uiState.update {
                    it.copy(dialogState = DialogState.None)
                }
            }

            is EditQuestUiEvent.OnCreateQuestCategory -> {
                viewModelScope.launch {
                    addQuestCategoryUseCase.invoke(
                        questCategoryEntity = QuestCategoryEntity(
                            text = event.value
                        )
                    )
                }
            }

            is EditQuestUiEvent.OnSelectQuestCategory -> {
                _selectedCategory.value = event.questCategoryEntity
            }

            is EditQuestUiEvent.OnRemoveReminder -> {
                val updatedTimes = _uiState.value.notificationTriggerTimes.toMutableList().apply {
                    removeAt(event.index)
                }
                _uiState.value = _uiState.value.copy(notificationTriggerTimes = updatedTimes)
            }

            is EditQuestUiEvent.OnCreateReminder -> {
                val updatedTimes = _uiState.value.notificationTriggerTimes.toMutableList().apply {
                    add(event.timestamp)
                }

                _uiState.update {
                    it.copy(
                        notificationTriggerTimes = updatedTimes,
                        addingDateTimeState = AddingDateTimeState.DATE
                    )
                }
            }

            is EditQuestUiEvent.OnCreateSubQuest -> {
                _uiState.update {
                    it.copy(
                        subTasks = _uiState.value.subTasks + SubQuestEntity(text = "", questId = id.toLong())
                    )
                }
            }

            is EditQuestUiEvent.OnUpdateSubQuest -> {
                _uiState.update { state ->
                    state.copy(
                        subTasks = state.subTasks.mapIndexed { i, subTask ->
                            if (i == event.index) subTask.copy(text = event.value) else subTask
                        }
                    )
                }
            }

            is EditQuestUiEvent.OnRemoveSubQuest -> {
                viewModelScope.launch {
                    _uiState.value.subTasks.mapIndexed { i, subTask ->
                        if (i == event.index) {
                            deleteSubQuestUseCase.invoke(subTask.id)
                        }
                    }
                }
            }

            is EditQuestUiEvent.OnSetDueDate -> {
                _uiState.update {
                    it.copy(
                        dueDate = event.timestamp,
                        dialogState = DialogState.None
                    )
                }
            }

            is EditQuestUiEvent.OnRemoveDueDate -> {
                _uiState.update {
                    it.copy(
                        dueDate = 0L,
                        dialogState = DialogState.None
                    )
                }
            }

            is EditQuestUiEvent.OnUpdateReminderState -> {
                _uiState.update {
                    it.copy(addingDateTimeState = event.value)
                }
            }

            else -> Unit
        }
    }
}