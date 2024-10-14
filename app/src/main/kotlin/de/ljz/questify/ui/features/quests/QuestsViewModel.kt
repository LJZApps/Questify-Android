package de.ljz.questify.ui.features.quests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.ljz.questify.data.repositories.AppUserRepository
import de.ljz.questify.data.shared.Points
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestsViewModel(
    private val appUserRepository: AppUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestsUIState())
    val uiState: StateFlow<QuestsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            appUserRepository.getAppUser().collect { appUser ->
                _uiState.update { currentState ->
                    currentState.copy(
                        userPoints = appUser.points
                    )
                }
            }
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