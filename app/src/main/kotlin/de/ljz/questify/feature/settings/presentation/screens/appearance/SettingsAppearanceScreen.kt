package de.ljz.questify.feature.settings.presentation.screens.appearance

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsMenuLink
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSection
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSwitch
import de.ljz.questify.feature.settings.data.models.ThemeBehavior
import de.ljz.questify.feature.settings.presentation.dialogs.ColorPickerDialog
import de.ljz.questify.feature.settings.presentation.dialogs.PaletteStyleDialog
import de.ljz.questify.feature.settings.presentation.dialogs.ThemeBehaviorDialog

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsAppearanceScreen(
    mainNavController: NavHostController,
    viewModel: SettingsAppearanceViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    SettingsAppearanceScreen(
        uiState = uiState
    ) { event ->
        when (event) {
            is SettingsAppearanceUiEvent.NavigateUp -> mainNavController.navigateUp()
            else -> viewModel.onUiEvent(event)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SettingsAppearanceScreen(
    uiState: SettingsAppearanceUiState,
    onUiEvent: (SettingsAppearanceUiEvent) -> Unit
) {
    val themOptions = listOf(
        ThemeItem(
            stringResource(R.string.settings_screen_theme_system),
            ThemeBehavior.SYSTEM_STANDARD
        ),
        ThemeItem(stringResource(R.string.settings_screen_theme_dark), ThemeBehavior.DARK),
        ThemeItem(stringResource(R.string.settings_screen_theme_light), ThemeBehavior.LIGHT),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_appearance_screen_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent.invoke(SettingsAppearanceUiEvent.NavigateUp)
                        },
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        ExpressiveSettingsSection(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            /*ExpressiveSettingsSwitch(
                state = uiState.dynamicColorsEnabled,
                title = stringResource(R.string.settings_screen_dynamic_colors_title),
                subtitle = stringResource(R.string.settings_screen_dynamic_colors_subtitle),
                icon = { Icon(Icons.Outlined.Colorize, contentDescription = null) },
                onCheckedChange = {
                    onUiEvent.invoke(
                        SettingsAppearanceUiEvent.UpdateDynamicColorsEnabled(
                            it
                        )
                    )
                }
            )*/

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_screen_app_theme_title),
                subtitle = themOptions.first { it.behavior == uiState.themeBehavior }.text,
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
                    onUiEvent.invoke(SettingsAppearanceUiEvent.ShowDarkModeDialog)
                }
            )

            ExpressiveSettingsSwitch(
                state = uiState.isAmoled,
                title = stringResource(R.string.settings_appearance_screen_amoled_title),
                subtitle = stringResource(R.string.settings_appearance_screen_amoled_description),
                icon = { Icon(Icons.Outlined.Contrast, contentDescription = null) },
                onCheckedChange = { enabled ->
                    onUiEvent.invoke(SettingsAppearanceUiEvent.UpdateIsAmoledEnabled(enabled))
                },
            )

            /*AnimatedVisibility(!uiState.dynamicColorsEnabled) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {


                    ExpressiveSettingsMenuLink(
                        title = stringResource(R.string.settings_appearance_screen_theme_style_title),
                        subtitle = PaletteStyle.entries.first { it.ordinal == uiState.paletteStyle.ordinal }.name,
                        icon = { Icon(Icons.Outlined.Style, contentDescription = null) },
                        onClick = {
                            onUiEvent.invoke(SettingsAppearanceUiEvent.ShowPaletteStyleDialog)
                        }
                    )

                    if (uiState.paletteStyle != PaletteStyle.Monochrome) {
                        ExpressiveSettingsMenuLink(
                            title = stringResource(R.string.settings_appearance_screen_app_tint_title),
                            subtitle = stringResource(R.string.settings_appearance_screen_app_tint_description),
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(Color(uiState.appColor.toColorInt()))
                                )
                            },
                            onClick = {
                                onUiEvent.invoke(SettingsAppearanceUiEvent.ShowColorPickerDialog)
                            }
                        )
                    }
                }
            }*/
        }

        if (uiState.darkModeDialogVisible) {
            ThemeBehaviorDialog(
                themeBehavior = uiState.themeBehavior,
                onConfirm = { behavior ->
                    onUiEvent.invoke(SettingsAppearanceUiEvent.UpdateThemeBehavior(behavior))
                    onUiEvent.invoke(SettingsAppearanceUiEvent.HideDarkModeDialog)
                },
                onDismiss = {
                    onUiEvent.invoke(SettingsAppearanceUiEvent.HideDarkModeDialog)
                }
            )
        }

        if (uiState.paletteStyleDialogVisible) {
            PaletteStyleDialog(
                paletteStyle = uiState.paletteStyle,
                onConfirm = { style ->
                    onUiEvent.invoke(SettingsAppearanceUiEvent.UpdatePaletteStyle(style))
                    onUiEvent.invoke(SettingsAppearanceUiEvent.HidePaletteStyleDialog)
                },
                onDismiss = {
                    onUiEvent.invoke(SettingsAppearanceUiEvent.HidePaletteStyleDialog)
                }
            )
        }

        if (uiState.colorPickerDialogVisible) {
            ColorPickerDialog(
                appColor = uiState.appColor,
                onConfirm = { color ->
                    onUiEvent.invoke(SettingsAppearanceUiEvent.UpdateAppColor(color))
                    onUiEvent.invoke(SettingsAppearanceUiEvent.HideColorPickerDialog)
                },
                onDismiss = {
                    onUiEvent.invoke(SettingsAppearanceUiEvent.HideColorPickerDialog)
                }
            )
        }
    }
}