package de.ljz.questify.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.ljz.questify.data.repositories.AppUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appUserRepository: AppUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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

}