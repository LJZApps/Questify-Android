package de.ljz.questify.feature.quests.presentation.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateCategoryDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var listTitle by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Liste erstellen")
        },
        text = {
            OutlinedTextField(
                value = listTitle,
                onValueChange = {
                    listTitle = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Name der Liste"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                )
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
                shapes = ButtonDefaults.shapes()
            ) {
                Text(
                    text = "Abbrechen"
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(listTitle)
                },
                shapes = ButtonDefaults.shapes()
            ) {
                Text(
                    text = "Erstellen"
                )
            }
        }
    )
}