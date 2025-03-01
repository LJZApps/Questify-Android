package de.ljz.questify.ui.features.settings.settings_appearance

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Style
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import com.materialkolor.PaletteStyle
import de.ljz.questify.R
import de.ljz.questify.ui.features.settings.settings_appearance.components.ColorPickerDialog
import de.ljz.questify.ui.features.settings.settings_appearance.components.PaletteStyleDialog
import de.ljz.questify.ui.features.settings.settings_appearance.components.ThemeBehaviorDialog
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.util.NavBarConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAppearanceScreen(
    mainNavController: NavHostController,
    viewModel: SettingsAppearanceViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

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

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.settings_appearance_screen_title))
                },
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
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SettingsSwitch(
                state = uiState.dynamicColorsEnabled,
                title = { Text(text = stringResource(R.string.settings_screen_dynamic_colors_title)) },
                subtitle = { Text(text = stringResource(R.string.settings_screen_dynamic_colors_subtitle)) },
                icon = { Icon(Icons.Outlined.Colorize, contentDescription = null) },
                onCheckedChange = viewModel::updateDynamicColorsEnabled,
            )

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

            AnimatedVisibility(!uiState.dynamicColorsEnabled) {
                Column {
                    SettingsSwitch(
                        state = uiState.isAmoled,
                        title = { Text(text = stringResource(R.string.settings_appearance_screen_amoled_title)) },
                        subtitle = { Text(text = stringResource(R.string.settings_appearance_screen_amoled_description)) },
                        icon = { Icon(Icons.Outlined.Contrast, contentDescription = null) },
                        onCheckedChange = viewModel::updateIsAmoledEnabled,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.settings_appearance_screen_theme_style_title)) },
                        subtitle = {
                            Text(
                                text = PaletteStyle.entries.first { it.ordinal == uiState.paletteStyle.ordinal }.name
                            )
                        },
                        icon = { Icon(Icons.Outlined.Style, contentDescription = null) },
                        onClick = {
                            viewModel.showPaletteStyleDialog()
                        }
                    )

                    if(uiState.paletteStyle != PaletteStyle.Monochrome) {
                        SettingsMenuLink(
                            title = { Text(text = stringResource(R.string.settings_appearance_screen_app_tint_title)) },
                            subtitle = {
                                Text(
                                    text = stringResource(R.string.settings_appearance_screen_app_tint_description)
                                )
                            },
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(Color(android.graphics.Color.parseColor(uiState.appColor)))
                                )
                            },
                            onClick = {
                                viewModel.showColorPickerDialog()
                            }
                        )
                    }
                }
            }
        }

        if (uiState.darkModeDialogVisible) {
            ThemeBehaviorDialog(
                themeBehavior = uiState.themeBehavior,
                onConfirm = { behavior ->
                    viewModel.updateThemeBehavior(behavior)
                    viewModel.hideDarkModeDialog()
                },
                onDismiss = {
                    viewModel.hideDarkModeDialog()
                }
            )
        }

        if (uiState.paletteStyleDialogVisible) {
            PaletteStyleDialog(
                paletteStyle = uiState.paletteStyle,
                onConfirm = { style ->
                    viewModel.updatePaletteStyle(style)
                    viewModel.hidePaletteStyleDialog()
                },
                onDismiss = {
                    viewModel.hidePaletteStyleDialog()
                }
            )
        }

        if (uiState.colorPickerDialogVisible) {
            ColorPickerDialog(
                appColor = uiState.appColor,
                onConfirm = { color ->
                    viewModel.setAppColor(color)
                    viewModel.hideColorPickerDialog()
                },
                onDismiss = {
                    viewModel.hideColorPickerDialog()
                }
            )
        }
    }
}