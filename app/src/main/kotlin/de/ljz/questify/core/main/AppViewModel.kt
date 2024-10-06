package de.ljz.questify.core.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
  private val sessionManager: SessionManager,
  private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(AppUiState())
  val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

  private val _isAppReady = MutableStateFlow(false)
  val isAppReady: StateFlow<Boolean> = _isAppReady.asStateFlow()

  init {
    viewModelScope.launch {
      // Vorherige Abfrage der App-Einstellungen vor der UI-Update
      val appSettings = appSettingsRepository.getAppSettings().firstOrNull()

      _uiState.update {
        it.copy(
          isLoggedIn = sessionManager.isAccessTokenPresent(),
          isSetupDone = appSettings?.setupDone == true
        )
      }

      _isAppReady.update { true }
    }
  }
}
