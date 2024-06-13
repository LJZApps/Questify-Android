package de.ljz.questify.ui.features.register

data class RegisterUiState(
  // Usernames
  val email: String = "",
  val username: String = "",

  // Passwords
  val password: String = "",
  val confirmPassword: String = "",

  // Temporary user data saving
  val aboutMe: String = "",
  val birthday: Long = 0,
  val passwordVisible: Boolean = false,

  // Page count
  val pageCount: Int = 4
)