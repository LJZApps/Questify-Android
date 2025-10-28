package de.ljz.questify.feature.onboarding.presentation.screens.onboarding

sealed interface OnboardingUiEvent {
    object OnNextPage : OnboardingUiEvent
    data class OnScrollToIteration(val iteration: Int) : OnboardingUiEvent

    object OnOnboardingFinished : OnboardingUiEvent
}