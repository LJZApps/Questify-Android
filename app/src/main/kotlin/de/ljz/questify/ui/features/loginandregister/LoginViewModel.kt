package de.ljz.questify.ui.features.loginandregister

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.core.mvi.MviViewModel
import de.ljz.questify.data.repositories.LoginRepository
import de.ljz.questify.data.sharedpreferences.SessionManager
import de.ljz.questify.ui.features.loginandregister.LoginViewContract.Action
import de.ljz.questify.ui.features.loginandregister.LoginViewContract.Effect
import de.ljz.questify.ui.features.loginandregister.LoginViewContract.State
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginRepository: LoginRepository,
  private val contextProvider: ContextProvider,
  private val sessionManager: SessionManager
) : MviViewModel<State, Action, Effect>(State()) {

  override fun onAction(action: Action) {
    when (action) {
      Action.OnLoginButtonClick -> checkData()

      Action.OnOpenRegisterButtonClick -> {
        sendEffect(Effect.NavigateRegisterScreen)
      }

      Action.OnOpenLoginButtonClick -> {
        sendEffect(Effect.NavigateLoginScreen)
      }
    }
  }

  fun checkData() {
    val username = state.value.loginState.username
    val password = state.value.loginState.password

    when {
      username.isEmpty() -> {
        updateState {
          copy(
            loginState = loginState.copy(
              loginErrorMessage = "Username cannot be empty",
              isLoginErrorShown = true,
            )
          )
        }
      }
      password.isEmpty() -> {
        updateState {
          copy(
            loginState = loginState.copy(
              loginErrorMessage = "Password cannot be empty",
              isLoginErrorShown = true,
            )
          )
        }
      }
      password.length < 8 -> {
        updateState {
          copy(
            loginState = loginState.copy(
              loginErrorMessage = "Password must be at least 8 characters long",
              isLoginErrorShown = true,
            )
          )
        }
      }
      else -> {
        // Daten sind gültig, Login-Prozess starten
        login()
      }
    }
  }

  private fun login() {
    updateState { copy(isLoading = true, loadingText = "Logging in") }
    viewModelScope.launch {
      loginRepository.login(
        username = state.value.loginState.username,
        password = state.value.loginState.password,
        onSuccess = {
          if (it.success) {
            sessionManager.setAccessToken(it.accessToken.token)
            sessionManager.setRefreshToken(it.refreshToken.token)
            sessionManager.setExpirationTime(it.accessToken.exp)

            updateState {
              copy(
                loadingText = "Done! Now setup your app."
              )
            }

            sendEffect(Effect.NavigateSetupScreen)
          }
        },
        onError = {
          updateState {
            copy(
              isLoading = false,
              loadingText = "",
              loginState = loginState.copy(
                loginErrorMessage = it.errorMessage.toString(),
                isLoginErrorShown = true,
              )
            )
          }
        }
      )
    }
  }

  fun dismissDialog() {
    updateState {
      copy(
        loginState = loginState.copy(
          isLoginErrorShown = false,
          loginErrorMessage = ""
        )
      )
    }
  }

  fun updatePassword(password: String) {
    updateState { copy(loginState = loginState.copy(password = password)) }
  }

  fun updateUsername(username: String) {
    updateState { copy(loginState = loginState.copy(username = username)) }
  }

  fun togglePasswordVisibility() {
    updateState { copy(loginState = loginState.copy(passwordVisible = !loginState.passwordVisible)) }
  }
}