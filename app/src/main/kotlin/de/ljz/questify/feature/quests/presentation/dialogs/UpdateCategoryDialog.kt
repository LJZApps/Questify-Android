package de.ljz.questify.feature.quests.presentation.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppTextButton
import de.ljz.questify.core.presentation.components.text_fields.AppOutlinedTextField
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UpdateCategoryDialog(
    questCategory: QuestCategoryEntity? = null,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var listTitle by remember { mutableStateOf(questCategory?.text ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.update_category_dialog_title))
        },
        text = {
            AppOutlinedTextField(
                value = listTitle,
                onValueChange = {
                    listTitle = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.text_field_name_of_list)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                singleLine = true
            )
        },
        dismissButton = {
            AppTextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
        },
        confirmButton = {
            AppTextButton(
                onClick = {
                    onConfirm(listTitle)
                }
            ) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }
    )
}