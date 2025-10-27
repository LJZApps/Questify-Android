package de.ljz.questify.core.presentation.components.bottom_sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppOutlinedButton
import de.ljz.questify.core.presentation.components.text_fields.AppOutlinedTextField
import kotlinx.coroutines.launch

/**
 * @param dismissButtonText set null to hide dismiss button
 */
@ExperimentalMaterial3Api
@Composable
fun InputBottomSheet(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier,
    dismissable: Boolean = true,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    iconTint: Color = LocalContentColor.current,
    initialValue: String = "",
    initialInputFocussed: Boolean = false,
    title: String,
    text: String? = null,
    icon: Painter? = null,
    maxLines: Int = 1,
    minLines: Int = 1,
    singleLine: Boolean = true,
    confirmationButtonColors: ButtonColors = ButtonDefaults.buttonColors(),
    dismissButtonColors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.primary
    ),
    confirmationButtonText: String,
    dismissButtonText: String? = null
) {
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    val properties = ModalBottomSheetProperties(
        shouldDismissOnBackPress = dismissable,
        shouldDismissOnClickOutside = dismissable
    )
    val sheetState = rememberModalBottomSheetState()
    val textFieldState = rememberTextFieldState(
        initialText = initialValue,
        initialSelection = TextRange(
            start = 0,
            end = initialValue.length
        )
    )

    LaunchedEffect(sheetState.isVisible) {
        if (initialInputFocussed && sheetState.isVisible) {
            focusRequester.requestFocus()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
        sheetState = sheetState,
        dragHandle = null,
        sheetGesturesEnabled = dismissable,
        containerColor = containerColor,
        contentColor = contentColor,
        properties = properties,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                icon?.let {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            text?.let {
                Text(
                    text = it,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            AppOutlinedTextField(
                state = textFieldState,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                ,
                placeholder = {
                    Text(
                        text = stringResource(R.string.text_field_name_of_list)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                onKeyboardAction = {
                    onConfirm(textFieldState.text.toString())
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dismissButtonText?.let { dismissText ->
                    AppOutlinedButton(
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                                onDismissRequest()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = dismissButtonColors
                    ) {
                        Text(dismissText)
                    }
                }

                Button(
                    onClick = {
                        onConfirm(textFieldState.text.toString())
                    },
                    modifier = Modifier.weight(1f),
                    colors = confirmationButtonColors
                ) {
                    Text(confirmationButtonText)
                }

            }
        }
    }
}