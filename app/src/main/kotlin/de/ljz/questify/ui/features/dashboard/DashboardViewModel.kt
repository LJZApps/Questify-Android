package de.ljz.questify.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppUserRepository
import de.ljz.questify.domain.repositories.QuestMasterRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val questRepository: QuestRepository,
    private val questMasterRepository: QuestMasterRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                questMasterRepository.getQuestMaster().collectLatest { questMaster ->
                    _uiState.update {
                        it.copy(
                            dashboardOnboardingDone = questMaster.dashboardOnboarding
                        )
                    }
                }
            }

            launch {
                questRepository.getQuests().collectLatest { questsList ->
                    _uiState.update {
                        it.copy(
                            quests = questsList
                        )
                    }
                }
            }

            launch {
                appUserRepository.getAppUser().collect { appUser ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userPoints = appUser.points,
                            userXp = appUser.xp,
                            userLevel = appUser.level
                        )
                    }
                }
            }
        }
    }

    fun setDashboardOnboardingDone() {
        viewModelScope.launch {
            questMasterRepository.setDashboardOnboardingDone()
        }
    }
}