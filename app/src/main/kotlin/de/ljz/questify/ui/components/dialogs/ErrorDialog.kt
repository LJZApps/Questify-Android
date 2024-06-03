package de.ljz.questify.ui.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import de.ljz.questify.ui.navigation.styles.ErrorDialogDestinationStyle

@Destination(style = ErrorDialogDestinationStyle::class)
@Composable
fun ErrorDialog(onDismiss: () -> Unit, title: String, message: String) {
  AlertDialog(
    onDismissRequest = {
       // Do nothing
    },
    icon = {
      Icon(Icons.Default.Error, contentDescription = null)
    },
    title = {
      Text(text = title)
    },
    text = {
      Text(
        text = message
      )
    },
    confirmButton = {
      TextButton(
        onClick = {
          onDismiss()
        }
      ) {
        Text(text = "Got it")
      }
    },
  )
}