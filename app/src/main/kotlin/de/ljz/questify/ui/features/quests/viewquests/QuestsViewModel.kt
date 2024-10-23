package de.ljz.questify.ui.features.quests.viewquests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.shared.Points
import de.ljz.questify.domain.repositories.AppUserRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestsViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestsUIState())
    val uiState: StateFlow<QuestsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                appUserRepository.getAppUser().collect { appUser ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userPoints = appUser.points
                        )
                    }
                }
            }

            launch {
                questRepository.getQuests().collectLatest { quests ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            quests = quests
                        )
                    }
                }
            }
        }
    }

    fun setQuestDone(id: Int, done: Boolean) {
        viewModelScope.launch {
            questRepository.setQuestDone(id, done)
        }
    }

    fun showQuestCreation() {
        _uiState.update {
            it.copy(
                createQuestSheetOpen = true
            )
        }
    }

    fun hideQuestCreation() {
        _uiState.update {
            it.copy(
                createQuestSheetOpen = false
            )
        }
    }

    fun addPoint() {
        viewModelScope.launch {
            appUserRepository.addPoint(Points.EASY)
        }
    }
}