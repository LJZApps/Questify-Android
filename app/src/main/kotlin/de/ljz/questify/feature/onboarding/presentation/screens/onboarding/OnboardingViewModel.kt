package de.ljz.questify.feature.onboarding.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.domain.use_cases.SetOnboardingDoneUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingDoneUseCase: SetOnboardingDoneUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = OnboardingUiState(
            currentPage = 0
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(event: OnboardingUiEvent) {
        when (event) {
            is OnboardingUiEvent.OnOnboardingFinished -> {
                viewModelScope.launch {
                    setOnboardingDoneUseCase.invoke()
                }
            }

            else -> Unit
        }
    }
}