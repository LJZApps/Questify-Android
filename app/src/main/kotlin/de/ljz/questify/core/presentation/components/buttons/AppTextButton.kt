package de.ljz.questify.core.presentation.components.buttons

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit),
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        content = content
    )
}