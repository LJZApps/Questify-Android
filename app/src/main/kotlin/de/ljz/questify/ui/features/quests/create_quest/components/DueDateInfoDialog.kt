package de.ljz.questify.ui.features.quests.create_quest.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DueDateInfoDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Verstanden")
            }
        },
        title = {
            Text("Fälligkeit ist nach Erstellung unveränderbar")
        },
        text = {
            Text("Sobald die Quest erstellt ist, bleibt die Fälligkeit unveränderbar. Plane deine Zeit gut, um die volle Belohnung zu sichern – nach Ablauf gibt’s nur die halbe Punktzahl. Schließe die Quest rechtzeitig ab und hol dir alles!")
        }
    )
}