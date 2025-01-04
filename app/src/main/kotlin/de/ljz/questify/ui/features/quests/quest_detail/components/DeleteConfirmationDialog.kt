package de.ljz.questify.ui.features.quests.quest_detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        icon = {
            Icon(Icons.Filled.Delete, contentDescription = null)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Löschen")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Abbrechen")
            }
        },
        title = {
            Text("Quest löschen?")
        },
        text = {
            Text("Bist du sicher, dass du diese Quest löschen möchtest? Dieser Vorgang kann nicht rückgängig gemacht werden.")
        }
    )
}