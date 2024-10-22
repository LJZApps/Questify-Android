package de.ljz.questify.ui.features.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import de.ljz.questify.ui.features.settings.components.CustomColorDialog
import de.ljz.questify.ui.features.settings.components.ThemeBehaviorDialog
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    mainNavController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val dynamicColorsEnabled = viewModel.dynamicColorsEnabled.collectAsState().value
    val customColor = viewModel.themeColor.collectAsState().value
    val themeBehavior = viewModel.themeBehavior.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value

    val colorOptions = listOf(
        CustomColorItem("Red", ThemeColor.RED),
        CustomColorItem("Green", ThemeColor.GREEN),
        CustomColorItem("Blue", ThemeColor.BLUE),
        CustomColorItem("Yellow", ThemeColor.YELLOW),
        CustomColorItem("Orange", ThemeColor.ORANGE),
        CustomColorItem("Purple", ThemeColor.PURPLE),
    )
    val themOptions = listOf(
        ThemeItem("System", ThemeBehavior.SYSTEM_STANDARD),
        ThemeItem("Dark Mode", ThemeBehavior.DARK),
        ThemeItem("Light Mode", ThemeBehavior.LIGHT),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(
                        onClick = { mainNavController.popBackStack() }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            SettingsGroup(
                title = { Text(text = "Theme") },
            ) {
                SettingsSwitch(
                    state = dynamicColorsEnabled,
                    title = { Text(text = "Dynamic Colors") },
                    subtitle = { Text(text = "Paint the app in your android colors") },
                    icon = { Icon(Icons.Outlined.Colorize, contentDescription = null) },
                    onCheckedChange = viewModel::updateDynamicColorsEnabled,
                )

                SettingsMenuLink(
                    title = { Text(text = "Custom color") },
                    subtitle = { Text(text = colorOptions.first { it.color == customColor }.text) },
                    icon = { Icon(Icons.Outlined.ColorLens, contentDescription = null) },
                    onClick = {
                        viewModel.showCustomColorDialog()
                    }
                )

                SettingsMenuLink(
                    title = { Text(text = "Dark mode") },
                    subtitle = { Text(text = themOptions.first { it.behavior == themeBehavior }.text) },
                    icon = { Icon(Icons.Outlined.DarkMode, contentDescription = null) },
                    onClick = {
                        viewModel.showDarkModeDialog()
                    }
                )
            }
        }

        if (uiState.customColorDialogVisible) {
            CustomColorDialog(
                selectedColor = customColor,
                onConfirm = { color ->
                    viewModel.updateCustomColor(color)
                    viewModel.hideCustomColorDialog()
                },
                onDismiss = {
                    viewModel.hideCustomColorDialog()
                }
            )
        }

        if (uiState.darkModeDialogVisible) {
            ThemeBehaviorDialog(
                themeBehavior = themeBehavior,
                onConfirm = { behavior ->
                    viewModel.updateThemeBehavior(behavior)
                    viewModel.hideDarkModeDialog()
                },
                onDismiss = {
                    viewModel.hideDarkModeDialog()
                }
            )
        }
    }
}