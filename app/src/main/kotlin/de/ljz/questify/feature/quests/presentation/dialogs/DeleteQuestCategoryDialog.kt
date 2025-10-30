package de.ljz.questify.feature.quests.presentation.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import de.ljz.questify.R
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DeleteQuestCategoryDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    questCategoryEntity: QuestCategoryEntity
) {
    val haptic = LocalHapticFeedback.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                    onConfirm()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                )
            ) {
                Text(text = "Liste löschen")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = {
            Text(
                text = "\"${questCategoryEntity.text}\" löschen?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        },
        text = {
            Text(
                text = "Deine Quests in dieser Liste werden nicht gelöscht."
            )
        }
    )
}