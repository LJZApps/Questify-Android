package de.ljz.questify.core.main

import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.main.MainViewContract.Action
import de.ljz.questify.core.main.MainViewContract.Effect
import de.ljz.questify.core.main.MainViewContract.State
import de.ljz.questify.core.mvi.MviViewModel
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
  private val sessionManager: SessionManager,
  private val appSettingsRepository: AppSettingsRepository,
) : MviViewModel<State, Action, Effect>(State()) {
  init {
    updateState {
      copy(
        isLoggedIn = sessionManager.isAccessTokenPresent(),
        isSetupDone = appSettingsRepository.getAppSettings().map { it.setupDone }
      )
    }
  }
}