package de.ljz.questify.core.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
  private val sessionManager: SessionManager,
  private val appSettingsRepository: AppSettingsRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(AppUiState())
  val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

  init {
    _uiState.update {
      it.copy(
        isLoggedIn = sessionManager.isAccessTokenPresent(),
        isSetupDone = appSettingsRepository.getAppSettings().map { it.setupDone }
      )
    }
  }
}