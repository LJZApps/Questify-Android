package de.ljz.questify.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.data.repositories.AppUserRepository
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
  private val contextProvider: ContextProvider,
  private val sessionManager: SessionManager,
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

  fun showCreateQuestDialog() {
    _uiState.update {
      it.copy(
        createQuestDialogVisible = true
      )
    }
  }

  fun hideCreateQuestDialog() {
    _uiState.update {
      it.copy(
        createQuestDialogVisible = false
      )
    }
  }

}