package de.ljz.questify.core.presentation.components.expressive_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ExpressiveMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            }
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f))
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (icon != null) {
            icon()
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}