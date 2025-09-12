package de.ljz.questify.feature.profile.presentation.screens.edit_profile

data class EditProfileUiState(
    val profilePictureUrl: String,
    val displayName: String,
    val aboutMe: String,
    val pickedProfilePicture: Boolean
)