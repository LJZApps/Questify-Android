package de.ljz.questify.feature.remind_again.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RemindAgainViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questNotificationRepository: QuestNotificationRepository
) : ViewModel() {

    /*private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()*/

    fun createQuestNotification(questId: Int, triggerTime: Long) {
        val questNotification = QuestNotificationEntity(
            questId = questId.toInt(),
            notifyAt = Date(triggerTime)
        )

        viewModelScope.launch {
            questNotificationRepository.addQuestNotification(questNotification)
        }
    }

}
