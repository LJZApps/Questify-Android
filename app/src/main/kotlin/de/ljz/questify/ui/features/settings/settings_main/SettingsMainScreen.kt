package de.ljz.questify.ui.features.settings.settings_main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsMenuLink
import de.ljz.questify.R
import de.ljz.questify.ui.features.settings.settings_appearance.SettingsAppearanceRoute
import de.ljz.questify.ui.features.settings.settings_help.SettingsHelpRoute
import de.ljz.questify.ui.features.settings.settings_appearance.components.CustomColorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsMainScreen(
    mainNavController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_screen_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = { mainNavController.navigateUp() }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsMenuLink(
                title = { Text(text = "Features") },
                subtitle = {
                    Text(
                        text = "Toggle app features"
                    )
                },
                icon = { Icon(Icons.Outlined.Extension, contentDescription = null) },
                onClick = {

                    /*mainNavController.navigate(SettingsAppearanceRoute)*/
                }
            )

            SettingsMenuLink(
                title = { Text(text = "Appearance") },
                subtitle = {
                    Text(
                        text = "Customize the app to your liking"
                    )
                },
                icon = { Icon(Icons.Outlined.ColorLens, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsAppearanceRoute)
                }
            )

            SettingsMenuLink(
                title = { Text(text = "Help") },
                subtitle = {
                    Text(
                        text = "Some app informations"
                    )
                },
                icon = { Icon(Icons.Outlined.HelpOutline, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsHelpRoute)
                }
            )

            /*SettingsGroup(
                title = { Text(text = stringResource(R.string.settings_screen_theme_title)) },
            ) {
                SettingsSwitch(
                    state = uiState.dynamicColorsEnabled,
                    title = { Text(text = stringResource(R.string.settings_screen_dynamic_colors_title)) },
                    subtitle = { Text(text = stringResource(R.string.settings_screen_dynamic_colors_subtitle)) },
                    icon = { Icon(Icons.Outlined.Colorize, contentDescription = null) },
                    onCheckedChange = viewModel::updateDynamicColorsEnabled,
                )

                SettingsSwitch(
                    state = uiState.isAmoled,
                    title = { Text(text = "AMOLED") },
                    subtitle = { Text(text = "Set the theme to pure dark to safe battery life") },
                    icon = { Icon(Icons.Outlined.Contrast, contentDescription = null) },
                    onCheckedChange = viewModel::updateIsAmoledEnabled,
                )



                AnimatedVisibility(!uiState.dynamicColorsEnabled) {
                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.settings_screen_custom_colors_title)) },
                        enabled = !uiState.dynamicColorsEnabled,
                        subtitle = {
                            Text(
                                text = colorOptions.first { it.color == uiState.themeColor }.text
                            )
                        },
                        icon = { Icon(Icons.Outlined.ColorLens, contentDescription = null) },
                        onClick = {
                            viewModel.showCustomColorDialog()
                        }
                    )
                }

                SettingsMenuLink(
                    title = { Text(text = stringResource(R.string.settings_screen_app_theme_title)) },
                    subtitle = { Text(text = themOptions.first { it.behavior == uiState.themeBehavior }.text) },
                    icon = {
                        Icon(
                            when (uiState.themeBehavior) {
                                ThemeBehavior.DARK -> Icons.Outlined.DarkMode
                                ThemeBehavior.LIGHT -> Icons.Outlined.LightMode
                                ThemeBehavior.SYSTEM_STANDARD -> {
                                    if (isSystemInDarkTheme())
                                        Icons.Outlined.DarkMode
                                    else
                                        Icons.Outlined.LightMode
                                }
                            },
                            contentDescription = null
                        )
                    },
                    onClick = {
                        viewModel.showDarkModeDialog()
                    }
                )
            }*/
        }

        if (uiState.customColorDialogVisible) {
            CustomColorDialog(
                selectedColor = uiState.themeColor,
                onConfirm = { color ->
                    viewModel.updateCustomColor(color)
                    viewModel.hideCustomColorDialog()
                },
                onDismiss = {
                    viewModel.hideCustomColorDialog()
                }
            )
        }
    }
}