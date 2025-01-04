package de.ljz.questify.ui.features.settings.settings_main.components

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
import de.ljz.questify.R
import de.ljz.questify.ui.features.settings.settings_main.CustomColorItem
import de.ljz.questify.ui.state.ThemeColor

@Composable
fun CustomColorDialog(
    selectedColor: ThemeColor,
    onConfirm: (ThemeColor) -> Unit,
    onDismiss: () -> Unit
) {
    val radioOptions = listOf(
        CustomColorItem(stringResource(R.string.settings_screen_color_red), ThemeColor.RED),
        CustomColorItem(stringResource(R.string.settings_screen_color_green), ThemeColor.GREEN),
        CustomColorItem(stringResource(R.string.settings_screen_color_blue), ThemeColor.BLUE),
        CustomColorItem(stringResource(R.string.settings_screen_color_yellow), ThemeColor.YELLOW),
        CustomColorItem(stringResource(R.string.settings_screen_color_orange), ThemeColor.ORANGE),
        CustomColorItem(stringResource(R.string.settings_screen_color_purple), ThemeColor.PURPLE),
    )
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(radioOptions.first { it.color == selectedColor })
    }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                radioOptions.forEach { colorItem ->
                    Row(
                        Modifier.fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (colorItem == selectedOption),
                                onClick = { onOptionSelected(colorItem) },
                                role = Role.RadioButton
                            )
                            .clip(RoundedCornerShape(6.dp))
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (colorItem == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = colorItem.text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
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
                            onConfirm(selectedOption.color)
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}