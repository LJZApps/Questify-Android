package de.ljz.questify.feature.quests.presentation.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import de.ljz.questify.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RenameCategoryDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    initialInputFocussed: Boolean = false,
    initialValue: String
) {
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    val textFieldState = rememberTextFieldState(
        initialText = initialValue,
        initialSelection = TextRange(
            start = 0,
            end = initialValue.length
        )
    )

    LaunchedEffect(Unit) {
        if (initialInputFocussed) {
            focusRequester.requestFocus()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.update_category_dialog_title))
        },
        text = {
            OutlinedTextField(
                state = textFieldState,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = stringResource(R.string.text_field_name_of_list)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                lineLimits = TextFieldLineLimits.MultiLine(
                    maxHeightInLines = 1
                ),
                onKeyboardAction = {
                    if (textFieldState.text.toString().trim().isNotEmpty()) {
                        onConfirm(textFieldState.text.toString())
                    }
                }
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(textFieldState.text.toString())
                },
                enabled = textFieldState.text.toString().trim().isNotEmpty() && textFieldState.text.toString().trim().lowercase() != initialValue.trim().lowercase()
            ) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }
    )
}