package de.ljz.questify.core.presentation.components.bottom_sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.core.presentation.components.buttons.AppButton
import de.ljz.questify.core.presentation.components.buttons.AppTextButton
import kotlinx.coroutines.launch

/**
 * @param dismissButtonText set null to hide dismiss button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationBottomSheet(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    dismissable: Boolean = false,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    title: String,
    text: String,
    icon: Painter? = null,
    confirmationButtonColors: ButtonColors = ButtonDefaults.buttonColors(),
    dismissButtonColors: ButtonColors = ButtonDefaults.textButtonColors(),
    confirmationButtonText: String,
    dismissButtonText: String? = null
) {
    val scope = rememberCoroutineScope()
    val properties = ModalBottomSheetProperties(
        shouldDismissOnBackPress = dismissable,
        shouldDismissOnClickOutside = dismissable
    )
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.padding(8.dp).navigationBarsPadding(),
        sheetState = sheetState,
        dragHandle = null,
        sheetGesturesEnabled = dismissable,
        containerColor = containerColor,
        contentColor = contentColor,
        properties = properties
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.let {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )

            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dismissButtonText?.let { dismissText ->
                    AppTextButton(
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

                AppButton(
                    onClick = {
                        onConfirm()
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