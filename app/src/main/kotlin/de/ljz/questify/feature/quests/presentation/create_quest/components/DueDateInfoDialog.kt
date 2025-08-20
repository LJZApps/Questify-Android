package de.ljz.questify.feature.quests.presentation.create_quest.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.ljz.questify.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
                },
                shapes = ButtonDefaults.shapes()
            ) {
                Text(stringResource(R.string.got_it))
            }
        },
        title = {
            Text(stringResource(R.string.due_date_info_dialog_title))
        },
        text = {
            Text(stringResource(R.string.due_date_info_dialog_description))
        }
    )
}