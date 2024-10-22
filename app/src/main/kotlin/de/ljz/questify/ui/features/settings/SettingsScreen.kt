package de.ljz.questify.ui.features.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BrightnessAuto
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.ModeNight
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
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import de.ljz.questify.R
import de.ljz.questify.ui.ds.theme.QuestifyTheme
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
        CustomColorItem(stringResource(R.string.settings_screen_color_red), ThemeColor.RED),
        CustomColorItem(stringResource(R.string.settings_screen_color_green), ThemeColor.GREEN),
        CustomColorItem(stringResource(R.string.settings_screen_color_blue), ThemeColor.BLUE),
        CustomColorItem(stringResource(R.string.settings_screen_color_yellow), ThemeColor.YELLOW),
        CustomColorItem(stringResource(R.string.settings_screen_color_orange), ThemeColor.ORANGE),
        CustomColorItem(stringResource(R.string.settings_screen_color_purple), ThemeColor.PURPLE),
    )
    val themOptions = listOf(
        ThemeItem(stringResource(R.string.settings_screen_theme_system), ThemeBehavior.SYSTEM_STANDARD),
        ThemeItem(stringResource(R.string.settings_screen_theme_dark), ThemeBehavior.DARK),
        ThemeItem(stringResource(R.string.settings_screen_theme_light), ThemeBehavior.LIGHT),
    )

    QuestifyTheme(
        transparentNavBar = true
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.settings_screen_title)) },
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
}