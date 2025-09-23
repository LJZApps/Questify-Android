package de.ljz.questify.feature.quests.presentation.screens.quests_overview

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.domain.repositories.app.SortingPreferencesRepository
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import de.ljz.questify.feature.quests.domain.use_cases.CompleteQuestUseCase
import de.ljz.questify.feature.quests.domain.use_cases.DeleteQuestUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestOverviewViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    private val sortingPreferencesRepository: SortingPreferencesRepository,
    private val questCategoryRepository: QuestCategoryRepository,
    private val completeQuestUseCase: CompleteQuestUseCase,
    private val deleteQuestUseCase: DeleteQuestUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = QuestOverviewUIState(
            dialogState = DialogState.None,
            allQuestPageState = AllQuestPageState(
                quests = emptyList(),
                sortingDirections = SortingDirections.ASCENDING,
                showCompleted = false
            ),
            questDoneDialogState = QuestDoneDialogState(
                questName = "",
                xp = 0,
                points = 0,
                newLevel = 0
            )
        )
    )
    val uiState: StateFlow<QuestOverviewUIState> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

    private val _selectedCategoryForUpdating = MutableStateFlow<QuestCategoryEntity?>(null)
    val selectedCategoryForUpdating: StateFlow<QuestCategoryEntity?> = _selectedCategoryForUpdating.asStateFlow()

    private val _effect = Channel<QuestOverviewUiEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch {
                questRepository.getQuests().collectLatest { quests ->
                    _uiState.update { it.copy(allQuestPageState = it.allQuestPageState.copy(quests = quests)) }
                }
            }

            launch {
                sortingPreferencesRepository.getQuestSortingPreferences()
                    .collectLatest { sortingPreferences ->
                        _uiState.update {
                            it.copy(
                                allQuestPageState = it.allQuestPageState.copy(
                                    sortingDirections = sortingPreferences.questSortingDirection,
                                    showCompleted = sortingPreferences.showCompletedQuests
                                )
                            )
                        }
                    }
            }

            launch {
                questCategoryRepository.getAllQuestCategories()
                    .collectLatest { questCategoryEntities ->
                        _categories.value = questCategoryEntities
                    }
            }
        }
    }

    fun onUiEvent(event: QuestOverviewUiEvent) {
        when (event) {
            is QuestOverviewUiEvent.OnQuestDelete -> {
                viewModelScope.launch {
                    deleteQuestUseCase.invoke(event.id)
                }
            }

            is QuestOverviewUiEvent.OnQuestChecked -> {

            }

            is QuestOverviewUiEvent.ShowDialog -> {
                _uiState.update {
                    it.copy(dialogState = event.dialogState)
                }
            }

            is QuestOverviewUiEvent.CloseDialog -> {
                _uiState.update {
                    it.copy(dialogState = DialogState.None)
                }
            }

            is QuestOverviewUiEvent.ShowUpdateCategoryDialog -> {
                _uiState.update {
                    it.copy(dialogState = DialogState.UpdateCategory)
                }

                _selectedCategoryForUpdating.update {
                    event.questCategoryEntity
                }
            }

            is QuestOverviewUiEvent.AddQuestCategory -> {
                viewModelScope.launch {
                    val questCategory = QuestCategoryEntity(text = event.value)
                    questCategoryRepository.addQuestCategory(questCategory)
                }
            }

            is QuestOverviewUiEvent.DeleteQuestCategory -> {
                viewModelScope.launch {
                    questCategoryRepository.deleteQuestCategory(event.questCategoryEntity)
                    sendEffect(QuestOverviewUiEffect.ShowSnackbar("Liste gelöscht"))
                }
            }

            else -> Unit
        }
    }

    private fun sendEffect(effect: QuestOverviewUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    fun updateQuestCategory(newText: String) {
        viewModelScope.launch {
            _selectedCategoryForUpdating.value?.let { selectedCategory ->
                questCategoryRepository.updateQuestCategory(selectedCategory, newText)

            }
        }
    }

    fun setQuestDone(quest: QuestEntity, context: Context) {
        viewModelScope.launch {
            launch {
                val result = completeQuestUseCase(quest)

                _uiState.update {
                    it.copy(
                        dialogState = DialogState.QuestDone,
                        questDoneDialogState = it.questDoneDialogState.copy(
                            xp = result.earnedXp,
                            points = result.earnedPoints,
                            newLevel = if (result.didLevelUp) result.newLevel else 0,
                            questName = quest.title
                        )
                    )
                }
            }

            launch {
                val notifications = questNotificationRepository.getNotificationsByQuestId(quest.id)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                notifications.forEach { notification ->
                    val intent = Intent(context, QuestNotificationReceiver::class.java)

                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        notification.id,
                        intent,
                        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    if (pendingIntent != null) {
                        alarmManager.cancel(pendingIntent)
                    }
                }

                questNotificationRepository.removeNotifications(quest.id)
            }
        }
    }

    fun updateQuestSortingDirection(sortingDirection: SortingDirections) {
        viewModelScope.launch {
            sortingPreferencesRepository.saveQuestSortingDirection(sortingDirection)
        }
    }

    fun updateShowCompletedQuests(showCompletedQuests: Boolean) {
        viewModelScope.launch {
            sortingPreferencesRepository.saveShowCompletedQuests(showCompletedQuests)
        }
    }

    fun hideQuestDoneDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                dialogState = DialogState.None,
                questDoneDialogState = currentState.questDoneDialogState.copy(
                    xp = 0,
                    points = 0,
                    newLevel = 0,
                    questName = ""
                )
            )
        }
    }


    fun showUpdateCategoryDialog(questCategory: QuestCategoryEntity? = null) {
        _selectedCategoryForUpdating.value = questCategory

        _uiState.update {
            it.copy(
                dialogState = DialogState.UpdateCategory,
            )
        }
    }

    fun hideUpdateCategoryDialog() {
        _selectedCategoryForUpdating.value = null

        _uiState.update {
            it.copy(
                dialogState = DialogState.None,
            )
        }
    }


}