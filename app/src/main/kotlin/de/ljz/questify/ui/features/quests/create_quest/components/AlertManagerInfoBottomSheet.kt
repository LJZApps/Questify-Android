package de.ljz.questify.ui.features.quests.create_quest.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.features.main.dialogs.CreateQuestDialogState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertManagerInfoBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
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
                text = "Bitte aktiviere die Berechtigung, Wecker und Erinnerungen erstellen zu können",
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Die App braucht diese Berechtigung, um eine Erinnerungen zur korrekten Zeit anzeigen zu können.\nDiese Berechtigung wird ab Android 11 benötigt, damit die App richtig funktionieren kann.",
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    onConfirm()
                },
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("Einstellungen öffnen")
            }
        }
    }

}