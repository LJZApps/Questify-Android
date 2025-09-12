package de.ljz.questify.feature.first_setup.presentation.screens.first_setup

import android.net.Uri

data class FirstSetupUiState(
    val userSetupPageUiState: UserSetupPageUiState,
    val currentPage: Int
)

data class UserSetupPageUiState(
    val displayName: String,
    val aboutMe: String,
    val imageUri: Uri?,
    val pickedProfilePicture: Boolean
)