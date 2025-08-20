package de.ljz.questify.feature.profile.view_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.domain.repositories.app.AppUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewProfileViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ViewProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            appUserRepository.getAppUser().collectLatest { appUser ->
                _uiState.update { currentState ->
                    currentState.copy(
                        displayName = appUser.displayName,
                        aboutMe = appUser.aboutMe,
                        profilePictureUrl = appUser.profilePicture
                    )
                }
            }
        }
    }
}