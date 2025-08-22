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
import de.ljz.questify.core.utils.QuestSorting
import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.profile.domain.repositories.AppUserRepository
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestOverviewViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    private val sortingPreferencesRepository: SortingPreferencesRepository,
    private val questCategoryRepository: QuestCategoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestOverviewUIState())
    val uiState: StateFlow<QuestOverviewUIState> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<QuestCategoryEntity>>(emptyList())
    val categories: StateFlow<List<QuestCategoryEntity>> = _categories.asStateFlow()

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
                                    sortingBy = sortingPreferences.questSorting,
                                    sortingDirections = sortingPreferences.questSortingDirection,
                                    showCompleted = sortingPreferences.showCompletedQuests
                                )
                            )
                        }
                    }
            }
        }

        viewModelScope.launch {
            loadCategories()
        }
    }

    fun addQuestCategory(questCategory: QuestCategoryEntity) {
        viewModelScope.launch {
            questCategoryRepository.addQuestCategory(questCategory)
        }
    }

    fun addDummyQuestCategory() {
        viewModelScope.launch {
            val questCategory = QuestCategoryEntity(text = "ChillyMilly")
            questCategoryRepository.addQuestCategory(questCategory)
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            questCategoryRepository.getAllQuestCategories().collectLatest { questCategoryEntities ->
                _categories.value = questCategoryEntities
            }
        }
    }

    fun setQuestDone(quest: QuestEntity, context: Context) {
        viewModelScope.launch {
            launch {
                questRepository.setQuestDone(quest.id, true)
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

                appUserRepository.addPointsAndXp(
                    difficulty = quest.difficulty,
                    earnedStats = { xp, points, level ->
                        _uiState.update {
                            it.copy(
                                questDoneDialogState = it.questDoneDialogState.copy(
                                    visible = true,
                                    xp = xp,
                                    points = points,
                                    newLevel = level ?: 0,
                                    questName = quest.title
                                )
                            )
                        }
                    }
                )
            }
        }
    }


    fun deleteQuest(id: Int) {
        viewModelScope.launch {
            questRepository.deleteQuest(id)
        }
    }

    fun updateQuestSorting(questSorting: QuestSorting) {
        viewModelScope.launch {
            sortingPreferencesRepository.saveQuestSorting(questSorting)
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

    fun showSortingBottomSheet() {
        _uiState.update {
            it.copy(
                isSortingBottomSheetOpen = true
            )
        }
    }

    fun hideSortingBottomSheet() {
        _uiState.update {
            it.copy(
                isSortingBottomSheetOpen = false
            )
        }
    }

    fun hideQuestDoneDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                questDoneDialogState = currentState.questDoneDialogState.copy(
                    visible = false,
                    xp = 0,
                    points = 0,
                    newLevel = 0,
                    questName = ""
                )
            )
        }
    }
}