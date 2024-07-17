package de.ljz.questify.ui.features.loginandregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.data.repositories.LoginRepository
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginRepository: LoginRepository,
  private val contextProvider: ContextProvider,
  private val sessionManager: SessionManager,
) : ViewModel() {
  private val _uiState = MutableStateFlow(LoginAndRegisterUiState())
  val uiState: StateFlow<LoginAndRegisterUiState> = _uiState.asStateFlow()

  fun checkData(
    onSuccess: () -> Unit
  ) {
    val username = _uiState.value.loginState.username
    val password = _uiState.value.loginState.password

    when {
      username.isEmpty() -> {
        _uiState.update {
          it.copy(
            loginState = it.loginState.copy(
              loginErrorMessage = "Username cannot be empty",
              isLoginErrorShown = true,
            )
          )
        }
      }

      password.isEmpty() -> {
        _uiState.update {
          it.copy(
            loginState = it.loginState.copy(
              loginErrorMessage = "Password cannot be empty",
              isLoginErrorShown = true,
            )
          )
        }
      }

      password.length < 8 -> {
        _uiState.update {
          it.copy(
            loginState = it.loginState.copy(
              loginErrorMessage = "Password must be at least 8 characters long",
              isLoginErrorShown = true,
            )
          )
        }
      }

      else -> {
        // Daten sind gültig, Login-Prozess starten
        login(onSuccess)
      }
    }
  }

  private fun login(
    onSuccess: () -> Unit
  ) {
    _uiState.update {
      it.copy(
        isLoading = true,
        loadingText = "Logging in"
      )
    }
    viewModelScope.launch {
      loginRepository.login(
        username = _uiState.value.loginState.username,
        password = _uiState.value.loginState.password,
        onSuccess = {
          if (it.success) {
            sessionManager.setAccessToken(it.accessToken)

            _uiState.update {
              it.copy(
                loadingText = "Done! Now setup your app."
              )
            }

            onSuccess.invoke()
          }
        },
        onError = {errorResponse ->
          _uiState.update {
            it.copy(
              isLoading = false,
              loadingText = "",
              loginState = it.loginState.copy(
                loginErrorMessage = errorResponse.errorMessage.toString(),
                isLoginErrorShown = true,
              )
            )
          }
        }
      )
    }
  }

  fun dismissDialog() {
    _uiState.update {
      it.copy(
        loginState = it.loginState.copy(
          isLoginErrorShown = false,
          loginErrorMessage = ""
        )
      )
    }
  }

  fun updatePassword(password: String) {
    _uiState.update {
      it.copy(
        loginState = it.loginState.copy(
          password = password
        )
      )
    }
  }

  fun updateUsername(username: String) {
    _uiState.update {
      it.copy(
        loginState = it.loginState.copy(
          username = username
        )
      )
    }
  }

  fun togglePasswordVisibility() {
    _uiState.update {
      it.copy(
        loginState = it.loginState.copy(
          passwordVisible = !it.loginState.passwordVisible
        )
      )
    }
  }
}