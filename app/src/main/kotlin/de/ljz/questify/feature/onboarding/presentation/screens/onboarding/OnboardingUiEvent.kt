package de.ljz.questify.feature.onboarding.presentation.screens.onboarding

sealed interface OnboardingUiEvent {
    object OnNextPage : OnboardingUiEvent
}