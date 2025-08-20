package de.ljz.questify.ui.features.remind_again

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.repositories.quests.QuestNotificationRepository
import de.ljz.questify.domain.repositories.quests.QuestRepository
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
