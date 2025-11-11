package de.ljz.questify.feature.profile.presentation.screens.edit_profile

sealed interface EditProfileUiEvent {
    data object NavigateUp : EditProfileUiEvent

    data class SaveProfile(val profilePictureUrl: String) : EditProfileUiEvent

    data class UpdateProfilePicture(val profilePictureUrl: String) : EditProfileUiEvent

    data class UpdateDisplayName(val displayName: String) : EditProfileUiEvent
    data class UpdateAboutMe(val aboutMe: String) : EditProfileUiEvent
}