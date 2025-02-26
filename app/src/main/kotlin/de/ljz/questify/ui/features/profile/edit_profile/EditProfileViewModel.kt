package de.ljz.questify.ui.features.profile.edit_profile

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.app.AppUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            appUserRepository.getAppUser().collectLatest { appUser ->
                _uiState.update { currentState ->
                    currentState.copy(
                        displayName = appUser.displayName,
                        aboutMe = appUser.aboutMe,
                        profilePictureUrl = appUser.profilePicture
                    )
                }
            }
        }
    }

    fun saveProfile(profilePictureUrl: String) {
        viewModelScope.launch {
            appUserRepository.saveProfile(
                displayName = _uiState.value.displayName,
                aboutMe = _uiState.value.aboutMe,
                imageUri = _uiState.value.profilePictureUrl
            )
        }
    }

    fun updateProfilePicture(profilePictureUrl: String) {
        _uiState.update {
            it.copy(profilePictureUrl = profilePictureUrl)
        }
    }

    fun updateDisplayName(displayName: String) {
        _uiState.update {
            it.copy(displayName = displayName)
        }
    }

    fun updateAboutMe(aboutMe: String) {
        _uiState.update {
            it.copy(aboutMe = aboutMe)
        }
    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val fileName = "profile_${UUID.randomUUID()}.jpg"
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)

            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
            return file.absolutePath
        }
        return null
    }
}