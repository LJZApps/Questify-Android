package de.ljz.questify.feature.settings.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.profile.domain.repositories.AppUserRepository
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val appUserRepository: AppUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = SettingsUIState(
            userName = "",
            userProfilePicture = "",
            aboutMe = ""
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            appUserRepository.getAppUser().collectLatest { appUser ->
                _uiState.update {
                    it.copy(
                        userName = appUser.displayName,
                        userProfilePicture = appUser.profilePicture,
                        aboutMe = appUser.aboutMe
                    )
                }
            }
        }
    }
}