package de.ljz.questify.ui.features.login_and_register.register

data class RegisterUiState(
    // Usernames
    val email: String = "",
    val username: String = "",

    // Passwords
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,

    // Temporary user data saving
    val displayName: String = "",
    val aboutMe: String = "",
    val birthday: Long = 0,

    // Page setup
    val pageCount: Int = 4,
    val backEnabled: Boolean = true
)