package de.ljz.questify.core.presentation.components.tooltips

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BasicRichTooltip(
    title: String,
    text: String,
    dismissText: String,
    initialIsVisible: Boolean = false,
    isPersistent: Boolean = false,
    content: @Composable () -> Unit
) {
    val tooltipState = rememberTooltipState(
        initialIsVisible = initialIsVisible,
        isPersistent = isPersistent
    )

    TooltipBox(
        state = tooltipState,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = { Text(title) },
                text = { Text(text) },
                action = {
                    TextButton(
                        onClick = {
                            tooltipState.dismiss()
                        },
                    ) {
                        Text(dismissText)
                    }
                }
            )
        }
    ) {
        content()
    }
}