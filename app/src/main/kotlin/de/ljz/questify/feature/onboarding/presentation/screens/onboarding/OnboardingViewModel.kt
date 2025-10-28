package de.ljz.questify.feature.onboarding.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = OnboardingUiState(
            currentPage = 0
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(event: OnboardingUiEvent) {
        when (event) {
            is OnboardingUiEvent.OnNextPage -> {
                _uiState.update { state ->
                    state.copy(
                        currentPage = state.currentPage + 1
                    )
                }
            }

            else -> Unit
        }
    }
}