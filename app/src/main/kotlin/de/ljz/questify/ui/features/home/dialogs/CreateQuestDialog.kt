package de.ljz.questify.ui.features.home.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CreateQuestDialog(
  onDismiss: () -> Unit,
  onConfirm: (String, String) -> Unit,
) {
  val (title, setTitle) = remember { mutableStateOf("") }
  val (description, setDescription) = remember { mutableStateOf("") }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(
      dismissOnBackPress = false,
      dismissOnClickOutside = false
    )
  ) {
    Card(
      shape = RoundedCornerShape(16.dp),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Create quest")
        OutlinedTextField(
          value = title,
          onValueChange = setTitle,
          modifier = Modifier.fillMaxWidth(),
          label = {
            Text(text = "Title")
          },
          shape = RoundedCornerShape(16.dp),
          singleLine = true
        )
        OutlinedTextField(
          value = description,
          onValueChange = setDescription,
          modifier = Modifier.fillMaxWidth(),
          label = {
            Text(text = "Description")
          },
          shape = RoundedCornerShape(16.dp),
          singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          horizontalArrangement = Arrangement.End,
          modifier = Modifier.fillMaxWidth()
        ) {
          TextButton(onClick = onDismiss) {
            Text("Dismiss")
          }
          Spacer(modifier = Modifier.width(8.dp))
          TextButton(onClick = { onConfirm(title, description) }) {
            Text("Confirm")
          }
        }
      }
    }
  }
}

@Preview
@Composable
private fun CreateQuestDialogPreview() {
  CreateQuestDialog(
    onDismiss = {},
    onConfirm = {_, _ ->

    }
  )
}