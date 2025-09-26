package de.ljz.questify.core.presentation.components.tooltips

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicPlainTooltip(
    text: String,
    position: TooltipAnchorPosition = TooltipAnchorPosition.Below,
    content: @Composable () -> Unit,
) {
    val tooltipState = rememberTooltipState()

    TooltipBox(
        state = tooltipState,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = position
        ),
        tooltip = {
            PlainTooltip{ Text(text) }
        }
    ) {
        content()
    }
}