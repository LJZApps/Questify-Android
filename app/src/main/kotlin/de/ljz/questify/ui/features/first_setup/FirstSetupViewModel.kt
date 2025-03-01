package de.ljz.questify.ui.features.first_setup

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.app.AppSettingsRepository
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
class FirstSetupViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val appUserRepository: AppUserRepository
): ViewModel() {
    private var _uiState = MutableStateFlow(FirstSetupUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                appUserRepository.getAppUser().collectLatest { appUser ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userSetupPageUiState = currentState.userSetupPageUiState.copy(
                                displayName = appUser.displayName,
                                aboutMe = appUser.aboutMe,
                                imageUri = Uri.parse(appUser.profilePicture)
                            ),
                        )
                    }
                }
            }
        }
    }

    fun setSetupDone(profilePicture: String) {
        viewModelScope.launch {
            launch {
                appUserRepository.saveProfile(
                    displayName = _uiState.value.userSetupPageUiState.displayName,
                    aboutMe = _uiState.value.userSetupPageUiState.aboutMe,
                    imageUri = profilePicture
                )
            }

            launch {
                appSettingsRepository.setOnboardingDone()
            }
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

    fun updateDisplayName(displayName: String) {
        _uiState.value = _uiState.value.copy(
            userSetupPageUiState = _uiState.value.userSetupPageUiState.copy(
                displayName = displayName
            )
        )
    }

    fun updateAboutMe(aboutMe: String) {
        _uiState.value = _uiState.value.copy(
            userSetupPageUiState = _uiState.value.userSetupPageUiState.copy(
                aboutMe = aboutMe
            )
        )
    }

    fun updateImageUri(imageUri: Uri?) {
        _uiState.value = _uiState.value.copy(
            userSetupPageUiState = _uiState.value.userSetupPageUiState.copy(
                pickedProfilePicture = true,
                imageUri = imageUri
            )
        )
    }
}