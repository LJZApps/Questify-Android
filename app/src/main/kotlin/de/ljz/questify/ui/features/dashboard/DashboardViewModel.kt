package de.ljz.questify.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppUserRepository
import de.ljz.questify.domain.repositories.QuestMasterRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            questMasterRepository.getQuestMaster().collectLatest { questMaster ->
                _uiState.update {
                    it.copy(
                        dashboardOnboardingDone = questMaster.dashboardOnboarding
                    )
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