package de.ljz.questify.ui.features.settings.settingsmain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import de.ljz.questify.BuildConfig
import de.ljz.questify.R
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.ui.features.settings.settingshelp.navigation.SettingsHelp
import de.ljz.questify.ui.features.settings.settingsmain.components.CustomColorDialog
import de.ljz.questify.ui.features.settings.settingsmain.components.ThemeBehaviorDialog
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import de.ljz.questify.util.NavBarConfig

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
        CustomColorItem(stringResource(R.string.settings_screen_color_red), ThemeColor.RED),
        CustomColorItem(stringResource(R.string.settings_screen_color_green), ThemeColor.GREEN),
        CustomColorItem(stringResource(R.string.settings_screen_color_blue), ThemeColor.BLUE),
        CustomColorItem(stringResource(R.string.settings_screen_color_yellow), ThemeColor.YELLOW),
        CustomColorItem(stringResource(R.string.settings_screen_color_orange), ThemeColor.ORANGE),
        CustomColorItem(stringResource(R.string.settings_screen_color_purple), ThemeColor.PURPLE),
    )
    val themOptions = listOf(
        ThemeItem(
            stringResource(R.string.settings_screen_theme_system),
            ThemeBehavior.SYSTEM_STANDARD
        ),
        ThemeItem(stringResource(R.string.settings_screen_theme_dark), ThemeBehavior.DARK),
        ThemeItem(stringResource(R.string.settings_screen_theme_light), ThemeBehavior.LIGHT),
    )

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

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
        ) {
            SettingsGroup(
                title = { Text(text = stringResource(R.string.settings_screen_theme_title)) },
            ) {
                SettingsSwitch(
                    state = dynamicColorsEnabled,
                    title = { Text(text = stringResource(R.string.settings_screen_dynamic_colors_title)) },
                    subtitle = { Text(text = stringResource(R.string.settings_screen_dynamic_colors_subtitle)) },
                    icon = { Icon(Icons.Outlined.Colorize, contentDescription = null) },
                    onCheckedChange = viewModel::updateDynamicColorsEnabled,
                )

                AnimatedVisibility(!dynamicColorsEnabled) {
                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.settings_screen_custom_colors_title)) },
                        enabled = !dynamicColorsEnabled,
                        subtitle = {
                            Text(
                                text = colorOptions.first { it.color == customColor }.text
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
                    subtitle = { Text(text = themOptions.first { it.behavior == themeBehavior }.text) },
                    icon = {
                        Icon(
                            when (themeBehavior) {
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
            }

            SettingsGroup(
                title = { Text(text = stringResource(R.string.settings_help_screen_help_title)) },
            ) {
                SettingsMenuLink(
                    title = { Text(text = "App-Berechtigungen") },
                    subtitle = { Text(text = "Berechtigungen, die zur vollen Funktionsfähigkeit benötigt werden") },
                    icon = {
                        Icon(Icons.Outlined.VerifiedUser, contentDescription = null)
                    },
                    onClick = {
                        mainNavController.navigate(SettingsPermissionRoute())
                    }
                )

                SettingsMenuLink(
                    title = { Text(text = stringResource(R.string.settings_help_screen_provide_feedback_title)) },
                    subtitle = { Text(text = stringResource(R.string.settings_help_screen_provide_feedback_subtitle)) },
                    icon = {
                        Icon(Icons.Outlined.Feedback, contentDescription = null)
                    },
                    onClick = {
                        mainNavController.navigate(SettingsHelp)
                    }
                )

                SettingsMenuLink(
                    title = {
                        Text("App-Info")
                    },
                    subtitle = {
                        Text(
                            text = "Version: ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
                        )
                    },
                    icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                    onClick = {
                        // TODO
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