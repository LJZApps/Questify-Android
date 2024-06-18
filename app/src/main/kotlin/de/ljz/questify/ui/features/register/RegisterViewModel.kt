package de.ljz.questify.ui.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.api.responses.common.ErrorResponse
import de.ljz.questify.data.repositories.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(RegisterUiState())
  val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

  fun validateEmail(
    onSuccess: () -> Unit,
    onFailure: (ErrorResponse) -> Unit
  ) {
    _uiState.update {
      it.copy(
        isLoading = true
      )
    }

    val email = _uiState.value.email
    when {
      email.isEmpty() -> {
        onFailure(ErrorResponse("empty_email", "Email cannot be empty"))
      }
      (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
        onFailure(ErrorResponse("invalid_email", "Email is not valid"))
      }
      else -> {
        viewModelScope.launch {
          registerRepository.validateEmail(
            email = email,
            onSuccess = {
              _uiState.update {
                it.copy(
                  isLoading = true
                )
              }
              onSuccess()
            },
            onError = {
              _uiState.update {
                it.copy(
                  isLoading = true
                )
              }
              onFailure(it)
            }
          )
        }
      }
    }
  }

  fun updateErrorMessage(errorMessage: String) {

  }

  fun updateEmail(email: String) {
    _uiState.update {
      it.copy(
        email = email
      )
    }
  }

  fun updatePassword(password: String) {
    _uiState.update {
      it.copy(
        password = password
      )
    }
  }

  fun updateConfirmPassword(confirmPassword: String) {
    _uiState.update {
      it.copy(
        confirmPassword = confirmPassword
      )
    }
  }

  fun updateUsername(username: String) {
    _uiState.update {
      it.copy(
        username = username
      )
    }
  }

  fun updateAboutMe(aboutMe: String) {
    _uiState.update {
      it.copy(
        aboutMe = aboutMe
      )
    }
  }

  fun updateBirthday(birthday: Long) {
    _uiState.update {
      it.copy(
        birthday = birthday
      )
    }
  }
}
