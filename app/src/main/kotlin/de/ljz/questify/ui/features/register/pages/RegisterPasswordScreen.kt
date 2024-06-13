package de.ljz.questify.ui.features.register.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegisterPasswordScreen(
  onPasswordChange: (String) -> Unit,
  onConfirmPasswordChange: (String) -> Unit,
  onPasswordConfirm: () -> Unit,
  onNextPage: () -> Unit,
  password: String,
  confirmPassword: String,
) {
  Box(modifier = Modifier.fillMaxSize()) {
    Text(text = "HALLUUU")
  }
}