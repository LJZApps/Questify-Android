package de.ljz.questify.feature.habits.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.ljz.questify.feature.habits.data.models.HabitType

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    notes: String? = null,
    types: List<HabitType>,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        headlineContent = {
            Text(
                text = title
            )
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (types.contains(HabitType.POSITIVE))
                    FilledTonalIconButton(
                        shapes = IconButtonDefaults.shapes(),
                        onClick = {

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }

                if (types.contains(HabitType.NEGATIVE))
                    FilledTonalIconButton(
                        shapes = IconButtonDefaults.shapes(),
                        onClick = {

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = null
                        )
                    }
            }

        },
        supportingContent = {
            notes?.let {
                Text(
                    text = it
                )
            }
        },
        modifier = modifier
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        )
    )
}