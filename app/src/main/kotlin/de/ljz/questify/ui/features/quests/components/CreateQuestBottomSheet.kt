package de.ljz.questify.ui.features.quests.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.features.home.dialogs.CreateQuestDialogState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestBottomSheet(
  sheetState: SheetState,
  onDismiss: () -> Unit,
  onConfirm: (CreateQuestDialogState) -> Unit,
) {
  val createQuestState = remember { mutableStateOf(CreateQuestDialogState()) }

  ModalBottomSheet(
    onDismissRequest = {
      // Verhindere das automatische Schließen durch Tastatur-Ereignisse
      if (sheetState.currentValue == SheetValue.Hidden) {
        onDismiss()
      }
    },
    sheetState = sheetState
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
    ) {
      // Titel
      Text(
        text = "Erstelle eine neue Quest",
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
        textAlign = TextAlign.Center
      )

      // Titel-Eingabe
      OutlinedTextField(
        value = createQuestState.value.title,
        onValueChange = { createQuestState.value = createQuestState.value.copy(title = it) },
        label = { Text("Quest Titel") },
        placeholder = { Text("Gib den Titel der Quest ein") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp)
      )

      // Beschreibung-Eingabe
      OutlinedTextField(
        value = createQuestState.value.description,
        onValueChange = { createQuestState.value = createQuestState.value.copy(description = it) },
        label = { Text("Beschreibung") },
        placeholder = { Text("Beschreibe die Quest") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp)
      )

      // Hauptquest Checkbox
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp)
      ) {
        Checkbox(
          checked = createQuestState.value.isMainQuest,
          onCheckedChange = { createQuestState.value = createQuestState.value.copy(isMainQuest = it) }
        )
        Text(
          text = "Hauptquest",
          modifier = Modifier.padding(start = 8.dp)
        )
      }

      // Buttons
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Abbrechen Button
        TextButton(
          onClick = {
            onDismiss()
          },
          modifier = Modifier.padding(8.dp)
        ) {
          Text("Abbrechen")
        }

        // Speichern Button
        Button(
          onClick = {
            if (createQuestState.value.title.isNotEmpty() && createQuestState.value.description.isNotEmpty()) {
              onConfirm(createQuestState.value)
            }
          },
          enabled = createQuestState.value.title.isNotEmpty() && createQuestState.value.description.isNotEmpty(),
          modifier = Modifier.padding(8.dp)
        ) {
          Text("Speichern")
        }
      }
    }
  }

}