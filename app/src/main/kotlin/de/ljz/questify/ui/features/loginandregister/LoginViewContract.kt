package de.ljz.questify.ui.features.loginandregister

object LoginViewContract {
  data class State(
    val isLoading: Boolean = false,
    val loadingText: String = "",
    val loginState: LoginState = LoginState(),
    val registerState: RegisterState = RegisterState(),
  )

  data class LoginState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoginErrorShown: Boolean = false,
    val loginErrorMessage: String = "",
  )

  data class RegisterState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoginErrorShown: Boolean = false,
    val loginErrorMessage: String = "",
  )

  sealed interface Action {
    data object OnOpenRegisterButtonClick : Action
    data object OnOpenLoginButtonClick : Action
    data object OnLoginButtonClick : Action
  }

  sealed interface Effect {
    data object NavigateBack: Effect
    data object NavigateRegisterScreen : Effect
    data object NavigateLoginScreen : Effect
    data object NavigateSetupScreen : Effect
  }
}