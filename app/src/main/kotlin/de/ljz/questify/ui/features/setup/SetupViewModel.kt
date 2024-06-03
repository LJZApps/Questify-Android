package de.ljz.questify.ui.features.setup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.mvi.MviViewModel
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.ui.features.setup.SetupViewContract.Action
import de.ljz.questify.ui.features.setup.SetupViewContract.Effect
import de.ljz.questify.ui.features.setup.SetupViewContract.State
import de.ljz.questify.ui.state.ThemeBehavior
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) : MviViewModel<State, Action, Effect>(State()) {
    override fun onAction(action: Action) {
        when (action) {
            Action.ChangeTheme -> {
                viewModelScope.launch {
                    appSettingsRepository.setDarkModeBehavior(ThemeBehavior.SYSTEM_STANDARD)
                }
            }
            else -> {}
        }
    }
}