package de.ljz.questify.ui.features.quests.viewquests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.shared.Points
import de.ljz.questify.domain.repositories.AppUserRepository
import de.ljz.questify.domain.repositories.QuestMasterRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewQuestsViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val questRepository: QuestRepository,
    private val questMasterRepository: QuestMasterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ViewQuestsUIState())
    val uiState: StateFlow<ViewQuestsUIState> = _uiState.asStateFlow()

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

            launch {
                questMasterRepository.getQuestMaster().collectLatest { questMaster ->
                    _uiState.update {
                        it.copy(
                            questOnboardingDone = questMaster.questsOnboarding
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

    fun setOnboardingDone() {
        viewModelScope.launch {
            questMasterRepository.setQuestsOnboardingDone()
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