package de.ljz.questify.core.presentation.components.expressive.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp

@Composable
fun ExpressiveSettingsSwitch(
    modifier: Modifier = Modifier,
    state: Boolean,
    title: String,
    subtitle: String? = null,
    icon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    val update: (Boolean) -> Unit = { boolean -> onCheckedChange(boolean) }

    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .toggleable(
                enabled = enabled,
                value = state,
                role = Role.Switch,
                onValueChange = { update(!state) },
            )
            .then(modifier),
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        supportingContent = {
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        leadingContent = {
            if (icon != null) {
                icon()
            }
        },
        trailingContent = {
            Switch(
                modifier = Modifier.clearAndSetSemantics { },
                enabled = enabled,
                checked = state,
                onCheckedChange = update
            )
        }
    )
}