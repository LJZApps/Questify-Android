package de.ljz.questify.ui.features.quests.quests_overview

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.app.AppUserRepository
import de.ljz.questify.domain.repositories.app.FeatureSettingsRepository
import de.ljz.questify.domain.repositories.quests.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class QuestOverviewViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository,
    private val appFeatureSettingsRepository: FeatureSettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestOverviewUIState())
    val uiState: StateFlow<QuestOverviewUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                questRepository.getQuests().collectLatest { quests ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            quests = quests
                        )
                    }
                }
            }
            launch {
                appFeatureSettingsRepository.getFeatureSettings().collectLatest { settings ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            featureSettings = currentState.featureSettings.copy(fastQuestAddingEnabled = settings.questFastAddingEnabled)
                        )
                    }
                }
            }
        }
    }

    fun setQuestDone(quest: QuestEntity, context: Context, onSuccess: (Int, Int, Int?) -> Unit) {
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
                        onSuccess.invoke(xp, points, level)
                    }
                )
            }
        }
    }

    fun createFastQuest(title: String) {
        viewModelScope.launch {
            val fastQuest = QuestEntity(
                title = title,
                difficulty = Difficulty.EASY,
                createdAt = Date()
            )
            questRepository.addMainQuest(fastQuest)
            updateFastAddingText("")
        }
    }

    fun deleteQuest(id: Int) {
        viewModelScope.launch {
            questRepository.deleteQuest(id)
        }
    }

    fun updateIsFastAddingFocused(focused: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isFastAddingFocused = focused
            )
        }
    }

    fun updateFastAddingText(text: String) {
        _uiState.update { currentState ->
            currentState.copy(
                fastAddingText = text
            )
        }
    }

}