package de.ljz.questify.ui.features.loginandregister

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.data.repositories.LoginRepository
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginScreenModel @Inject constructor(
  private val loginRepository: LoginRepository,
  private val contextProvider: ContextProvider,
  private val sessionManager: SessionManager,
) : StateScreenModel<LoginAndRegisterUiState>(LoginAndRegisterUiState()) {

  fun checkData(
    onSuccess: () -> Unit
  ) {
    val username = mutableState.value.loginState.username
    val password = mutableState.value.loginState.password

    when {
      username.isEmpty() -> {
        mutableState.update {
          it.copy(
            loginState = it.loginState.copy(
              loginErrorMessage = "Username cannot be empty",
              isLoginErrorShown = true,
            )
          )
        }
      }

      password.isEmpty() -> {
        mutableState.update {
          it.copy(
            loginState = it.loginState.copy(
              loginErrorMessage = "Password cannot be empty",
              isLoginErrorShown = true,
            )
          )
        }
      }

      password.length < 8 -> {
        mutableState.update {
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
    mutableState.update {
      it.copy(
        isLoading = true,
        loadingText = "Logging in"
      )
    }
    screenModelScope.launch {
      loginRepository.login(
        username = mutableState.value.loginState.username,
        password = mutableState.value.loginState.password,
        onSuccess = {
          if (it.success) {
            sessionManager.setAccessToken(it.accessToken)

            mutableState.update {
              it.copy(
                loadingText = "Done! Now setup your app."
              )
            }

            onSuccess.invoke()
          }
        },
        onError = {errorResponse ->
          mutableState.update {
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
    mutableState.update {
      it.copy(
        loginState = it.loginState.copy(
          isLoginErrorShown = false,
          loginErrorMessage = ""
        )
      )
    }
  }

  fun updatePassword(password: String) {
    mutableState.update {
      it.copy(
        loginState = it.loginState.copy(
          password = password
        )
      )
    }
  }

  fun updateUsername(username: String) {
    mutableState.update {
      it.copy(
        loginState = it.loginState.copy(
          username = username
        )
      )
    }
  }

  fun togglePasswordVisibility() {
    mutableState.update {
      it.copy(
        loginState = it.loginState.copy(
          passwordVisible = !it.loginState.passwordVisible
        )
      )
    }
  }
}