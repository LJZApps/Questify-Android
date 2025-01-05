package de.ljz.questify.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppUserRepository
import de.ljz.questify.domain.repositories.TutorialRepository
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
    private val questRepository: QuestRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
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

    fun toggleChangelogVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(changelogVisible = visible)
        }
    }

    fun dismissNewVersion() {
        _uiState.update {
            it.copy(newVersionVisible = false)
        }
    }
}