package de.ljz.questify.ui.features.settings.settings_appearance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.materialkolor.PaletteStyle
import de.ljz.questify.R
import de.ljz.questify.ui.features.settings.settings_appearance.ThemeItem
import de.ljz.questify.ui.state.ThemeBehavior

@Composable
fun PaletteStyleDialog(
    paletteStyle: PaletteStyle,
    onConfirm: (PaletteStyle) -> Unit,
    onDismiss: () -> Unit
) {
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(PaletteStyle.entries.first { it.name == paletteStyle.name })
    }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                PaletteStyle.entries.sortedBy { it.name }.forEach { style ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .selectable(
                                selected = (style == selectedOption),
                                onClick = { onOptionSelected(style) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (style == selectedOption),
                            onClick = null
                        )
                        Column {
                            Text(
                                text = style.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            if (style == PaletteStyle.TonalSpot) {
                                Text(
                                    text = stringResource(R.string.pallete_style_dialog_default),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(android.R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onConfirm(selectedOption)
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}