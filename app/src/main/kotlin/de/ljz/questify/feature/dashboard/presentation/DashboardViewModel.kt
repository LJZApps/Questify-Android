package de.ljz.questify.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.BuildConfig
import de.ljz.questify.feature.profile.domain.repositories.AppUserRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val questRepository: QuestRepository,
    private val appSettingsRepository: AppSettingsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                questRepository.getQuests().collectLatest { questsList ->
                    _uiState.update {
                        it.copy(
                            quests = questsList
                        )
                    }
                }
            }

            launch {
                appUserRepository.getAppUser().collect { appUser ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            appUser = appUser
                        )
                    }
                }
            }

            launch {
                appSettingsRepository.getAppSettings().collectLatest { appSettings ->
                    if (appSettings.lastOpenedVersion < BuildConfig.VERSION_CODE)
                        _uiState.update {
                            it.copy(newVersionVisible = true)
                        }
                }
            }
        }
    }

    fun toggleChangelogVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(changelogVisible = visible)
        }
    }

    fun dismissNewVersion() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(newVersionVisible = false)
            }

            appSettingsRepository.setLastOpenedVersion(BuildConfig.VERSION_CODE)
        }
    }
}