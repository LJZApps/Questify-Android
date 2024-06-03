package de.ljz.questify.ui.navigation.styles

import androidx.compose.ui.window.DialogProperties
import com.ramcosta.composedestinations.spec.DestinationStyle

object ErrorDialogDestinationStyle : DestinationStyle.Dialog {
  override val properties = DialogProperties(
    dismissOnClickOutside = false,
    dismissOnBackPress = false,
  )
}