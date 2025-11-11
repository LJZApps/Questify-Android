package de.ljz.questify.feature.profile.presentation.screens.view_profile

sealed interface ViewProfileUiEvent {
    object OnNavigateUp : ViewProfileUiEvent
    object OnNavigateToEditProfileScreen : ViewProfileUiEvent
}