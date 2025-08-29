package de.ljz.questify.feature.main.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.profile.domain.repositories.AppUserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
) : ViewModel() {
    val uiState: StateFlow<MainUiState> = appUserRepository.getAppUser()
        .map { appUser ->
            MainUiState(
                userName = appUser.displayName,
                userProfilePicture = appUser.profilePicture
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUiState()
        )
}