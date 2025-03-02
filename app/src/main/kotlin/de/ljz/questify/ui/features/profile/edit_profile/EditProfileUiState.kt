package de.ljz.questify.ui.features.profile.edit_profile

data class EditProfileUiState(
    val profilePictureUrl: String = "",
    val displayName: String = "Adventurer",
    val aboutMe: String = "",
    val pickedProfilePicture: Boolean = false
)