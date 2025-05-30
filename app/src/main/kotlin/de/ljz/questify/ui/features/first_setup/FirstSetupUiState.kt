package de.ljz.questify.ui.features.first_setup

import android.net.Uri

data class FirstSetupUiState(
    val userSetupPageUiState: UserSetupPageUiState = UserSetupPageUiState(),
    val currentPage: Int = 0
)

data class UserSetupPageUiState(
    val displayName: String = "",
    val aboutMe: String = "",
    val imageUri: Uri? = null,
    val pickedProfilePicture: Boolean = false
)