package de.ljz.questify.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppUserRepository
import de.ljz.questify.domain.repositories.TutorialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val tutorialRepository: TutorialRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                appUserRepository.getAppUser().collect { appUser ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userPoints = appUser.points,
                            userXP = appUser.xp,
                            userLevel = appUser.level,
                            userName = appUser.displayName
                        )
                    }
                }
            }

            launch {
                tutorialRepository.getQuestMaster().collectLatest { tutorials ->
                    _uiState.update {
                        it.copy(
                            tutorialsUiState = it.tutorialsUiState.copy(
                                dashboardOnboardingDone = tutorials.dashboardOnboarding,
                                questsOnboardingDone = tutorials.questsOnboarding,
                                trophiesOnboardingDone = tutorials.trophiesOnboarding,
                                tutorialsEnabled = tutorials.tutorialsEnabled
                            )
                        )
                    }
                }
            }
        }
    }

    fun toggleTutorials(enabled: Boolean) {
        viewModelScope.launch {
            tutorialRepository.toggleTutorials(enabled)
        }
    }

    fun setDashboardOnboardingDone() {
        viewModelScope.launch {
            tutorialRepository.setDashboardOnboardingDone()
        }
    }

    fun setQuestOnboardingDone() {
        viewModelScope.launch {
            tutorialRepository.setQuestsOnboardingDone()
        }
    }

    fun setTrophiesOnboardingDone() {
        viewModelScope.launch {
            tutorialRepository.setTrophiesOnboardingDone()
        }
    }
}