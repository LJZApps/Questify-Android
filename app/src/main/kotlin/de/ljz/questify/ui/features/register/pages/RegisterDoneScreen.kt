package de.ljz.questify.ui.features.register.pages

import androidx.compose.runtime.Composable
import de.ljz.questify.core.compose.UIModePreviews

@Composable
fun RegisterDoneScreen(
  onNextPage: () -> Unit
) {

}

@UIModePreviews
@Composable
fun RegisterDoneScreenPreview() {
  RegisterDoneScreen (
    onNextPage = {}
  )
}