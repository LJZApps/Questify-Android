package de.ljz.questify.core.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.utils.NotificationHelper
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val notificationHelper: NotificationHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val _isAppReady = MutableStateFlow(false)
    val isAppReady: StateFlow<Boolean> = _isAppReady.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                val appSettings = appSettingsRepository.getAppSettings().firstOrNull()

                _uiState.update {
                    it.copy(
                        isSetupDone = appSettings?.onboardingState == true
                    )
                }

                _isAppReady.update { true }
            }

            launch {
                notificationHelper.createNotificationChannel()
            }
        }
    }
}
