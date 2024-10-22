package de.ljz.questify.ui.features.settings.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.ljz.questify.ui.features.settings.ThemeItem
import de.ljz.questify.ui.state.ThemeBehavior

@Composable
fun ThemeBehaviorDialog(
    themeBehavior: ThemeBehavior,
    onConfirm: (ThemeBehavior) -> Unit,
    onDismiss: () -> Unit
) {
    val themOptions = listOf(
        ThemeItem("System", ThemeBehavior.SYSTEM_STANDARD),
        ThemeItem("Dark Mode", ThemeBehavior.DARK),
        ThemeItem("Light Mode", ThemeBehavior.LIGHT),
    )
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(themOptions.first { it.behavior == themeBehavior })
    }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                themOptions.forEach { colorItem ->
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
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onConfirm(selectedOption.behavior)
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}