package de.ljz.questify.ui.features.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.ui.state.ThemeBehavior
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
  private val appSettingsRepository: AppSettingsRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(SetupUiState())
  val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

  fun changeAppTheme() {
    viewModelScope.launch {
      appSettingsRepository.setDarkModeBehavior(ThemeBehavior.SYSTEM_STANDARD)
    }
  }
}