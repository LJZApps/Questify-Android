package de.ljz.questify.ui.features.loginandregister

data class LoginAndRegisterUiState(
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