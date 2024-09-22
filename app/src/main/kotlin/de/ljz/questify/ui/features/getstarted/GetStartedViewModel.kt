package de.ljz.questify.ui.features.getstarted

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import de.ljz.questify.data.repositories.AppSettingsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetStartedViewModel @Inject constructor(
  private val appSettingsRepository: AppSettingsRepository,
) : ScreenModel {

  fun setSetupDone() {
    screenModelScope.launch {
      appSettingsRepository.setOnboardingDone()
    }
  }

}