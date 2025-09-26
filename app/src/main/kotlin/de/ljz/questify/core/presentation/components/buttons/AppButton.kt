package de.ljz.questify.core.presentation.components.buttons

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier,
        content = content,
        enabled = enabled
    )
}