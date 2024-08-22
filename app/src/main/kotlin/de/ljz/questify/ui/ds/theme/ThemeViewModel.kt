package de.ljz.questify.ui.ds.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
  private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
  private val _themeBehavior = appSettingsRepository.getAppSettings().map { it.themeBehavior }
  var themeBehavior = ThemeBehavior.SYSTEM_STANDARD

  private val _themeColor = appSettingsRepository.getAppSettings().map { it.themeColor }
  var themeColor = ThemeColor.ORANGE

  init {
    viewModelScope.launch {
      _themeBehavior.collectLatest {
        themeBehavior = it
      }
    }

    viewModelScope.launch {
      _themeColor.collectLatest {
        themeColor = it
      }
    }
  }
}