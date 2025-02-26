package de.ljz.questify.ui.features.settings.settings_appearance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.materialkolor.PaletteStyle
import de.ljz.questify.R
import de.ljz.questify.ui.features.settings.settings_appearance.ThemeItem
import de.ljz.questify.ui.state.ThemeBehavior

@Composable
fun ColorPickerDialog(
    appColor: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedColor = remember { mutableStateOf(appColor) }
    val controller = rememberColorPickerController()

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
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 10.dp, vertical = 16.dp),
                    controller = controller,
                    onColorChanged = { envelope ->
                        selectedColor.value = "#${envelope.hexCode}"
                    },
                    initialColor = Color(android.graphics.Color.parseColor(appColor))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(android.graphics.Color.parseColor(selectedColor.value)))
                        .height(56.dp)
                )
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
                            onConfirm(selectedColor.value)
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}