package de.ljz.questify.ui.features.register

import android.util.Patterns
import cafe.adriel.voyager.core.model.StateScreenModel
import de.ljz.questify.data.api.responses.common.ErrorResponse
import de.ljz.questify.data.repositories.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class RegisterScreenModel @Inject constructor(
  private val registerRepository: RegisterRepository,
) : StateScreenModel<RegisterUiState>(RegisterUiState()) {
  private val _emailError = MutableStateFlow("")
  val emailError: StateFlow<String> = _emailError.asStateFlow()

  private val _passwordError = MutableStateFlow("")
  val passwordError: StateFlow<String> = _passwordError.asStateFlow()

  private val _confirmPasswordError = MutableStateFlow("")
  val confirmPasswordError: StateFlow<String> = _confirmPasswordError.asStateFlow()

  fun validateEmail(
    onSuccess: () -> Unit,
    onFailure: (ErrorResponse) -> Unit,
  ) {
    _emailError.value = ""

    val email = mutableState.value.email
    when {
      email.isEmpty() -> {
        _emailError.value = "Email cannot be empty"
        onFailure(ErrorResponse("empty_email", "Email cannot be empty"))
      }

      (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
        _emailError.value = "Email is not valid"
        onFailure(ErrorResponse("invalid_email", "Email is not valid"))
      }

      else -> {
        _emailError.value = ""
        onSuccess()
      }
    }
  }

  fun validatePassword(
    onSuccess: () -> Unit,
    onFailure: (ErrorResponse) -> Unit,
  ) {
    _passwordError.value = ""
    _confirmPasswordError.value = ""

    when {
      mutableState.value.password.isEmpty() -> {
        _passwordError.value = "Password cannot be empty"
        onFailure(ErrorResponse("empty_password", "Password cannot be empty"))
      }

      mutableState.value.password.length < 8 -> {
        _passwordError.value = "Password must be at least 8 characters"
        onFailure(ErrorResponse("invalid_password", "Password must be at least 8 characters"))
      }

      mutableState.value.password != mutableState.value.confirmPassword -> {
        _confirmPasswordError.value = "Passwords do not match"
        onFailure(ErrorResponse("password_mismatch", "Passwords do not match"))
      }

      else -> {
        _passwordError.value = ""
        _confirmPasswordError.value = ""
        onSuccess()
      }
    }
  }

  fun togglePasswordVisibility() {
    mutableState.update {
      it.copy(
        passwordVisible = !it.passwordVisible
      )
    }
  }

  fun toggleConfirmPasswordVisibility() {
    mutableState.update {
      it.copy(
        confirmPasswordVisible = !it.confirmPasswordVisible
      )
    }
  }

  fun hideAllPasswords() {
    mutableState.update {
      it.copy(
        passwordVisible = false,
        confirmPasswordVisible = false
      )
    }
  }

  fun updateEmail(email: String) {
    _emailError.value = ""
    mutableState.update {
      it.copy(
        email = email
      )
    }
  }

  fun updatePassword(password: String) {
    _passwordError.value = ""
    _confirmPasswordError.value = ""

    mutableState.update {
      it.copy(
        password = password
      )
    }
  }

  fun updateConfirmPassword(confirmPassword: String) {
    _passwordError.value = ""
    _confirmPasswordError.value = ""

    mutableState.update {
      it.copy(
        confirmPassword = confirmPassword
      )
    }
  }

  fun updateUsername(username: String) {
    mutableState.update {
      it.copy(
        username = username
      )
    }
  }

  fun updateAboutMe(aboutMe: String) {
    mutableState.update {
      it.copy(
        aboutMe = aboutMe
      )
    }
  }

  fun updateBirthday(birthday: Long) {
    mutableState.update {
      it.copy(
        birthday = birthday
      )
    }
  }

  fun updateDisplayName(displayName: String) {
    mutableState.update {
      it.copy(
        displayName = displayName
      )
    }
  }
}
