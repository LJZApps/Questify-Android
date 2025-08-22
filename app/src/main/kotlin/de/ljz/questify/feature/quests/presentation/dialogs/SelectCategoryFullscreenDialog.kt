package de.ljz.questify.feature.quests.presentation.dialogs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NewLabel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuItem
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSection
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SelectCategoryFullscreenDialog(
    categories: List<QuestCategoryEntity>,
    onCategorySelect: (QuestCategoryEntity) -> Unit,
    onCreateCategory: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var searchText by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        BasicTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                            singleLine = true,
                            decorationBox = @Composable { innerTextField ->
                                TextFieldDefaults.DecorationBox(
                                    value = searchText,
                                    enabled = true,
                                    innerTextField = innerTextField,
                                    singleLine = true,
                                    visualTransformation = VisualTransformation.None,
                                    interactionSource = interactionSource,
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        disabledContainerColor = Color.Transparent,
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "Liste suchen",
                                            fontStyle = FontStyle.Italic
                                        )
                                    },
                                    contentPadding = PaddingValues(0.dp),
                                )
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Dialog schließen"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                ExpressiveSettingsSection(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    val filteredLists = categories
                        .filter {
                            it.text.contains(
                                searchText,
                                ignoreCase = true
                            )
                        }

                    if (filteredLists.count() > 0) {
                        filteredLists
                            .forEach { list ->
                                ExpressiveMenuItem(
                                    title = list.text,
                                    onClick = {
                                        onCategorySelect(list)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.Label,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                    } else {
                        ExpressiveMenuItem(
                            title = "Liste \"$searchText\" erstellen",
                            onClick = {
                                onCreateCategory(searchText)
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.NewLabel,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}