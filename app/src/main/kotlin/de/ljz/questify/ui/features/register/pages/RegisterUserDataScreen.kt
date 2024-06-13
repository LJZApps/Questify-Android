package de.ljz.questify.ui.features.register.pages

import androidx.compose.runtime.Composable

@Composable
fun RegisterUserDataScreen(
  onNextPage: () -> Unit,
  onUsernameChange: (String) -> Unit,
  onAboutMeChange: (String) -> Unit,
  onBirthdayChange: (Long) -> Unit,
  username: String,
  aboutMe: String,
  birthday: Long
) {

}