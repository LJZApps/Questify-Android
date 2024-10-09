package de.ljz.questify.ui.features.getstarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.ljz.questify.data.repositories.AppSettingsRepository
import kotlinx.coroutines.launch

class GetStartedViewModel(
  private val appSettingsRepository: AppSettingsRepository,
) : ViewModel() {

  fun setSetupDone() {
    viewModelScope.launch {
      appSettingsRepository.setOnboardingDone()
    }
  }

}