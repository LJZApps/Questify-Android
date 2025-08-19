package de.ljz.questify.ui.features.settings.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive_settings.ExpressiveSettingsMenuLink
import de.ljz.questify.core.presentation.components.expressive_settings.ExpressiveSettingsSection
import de.ljz.questify.ui.features.settings.appearance.SettingsAppearanceRoute
import de.ljz.questify.ui.features.settings.help.SettingsHelpRoute

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
                        onClick = { mainNavController.navigateUp() },
                        shapes = IconButtonDefaults.shapes()
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
                .verticalScroll(rememberScrollState())
        ) {
            /*ExpressiveSettingsMenuLink(
                title =  stringResource(R.string.settings_main_screen_features_title) ,
                subtitle =  stringResource(R.string.settings_main_screen_features_description),
                icon = { Icon(Icons.Outlined.Extension, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsFeaturesRoute)
                }
            )*/

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_main_screen_appearance_title),
                subtitle = stringResource(R.string.settings_main_screen_appearance_description),
                icon = { Icon(Icons.Outlined.ColorLens, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsAppearanceRoute)
                }
            )

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_main_screen_help_title),
                subtitle = stringResource(R.string.settings_main_screen_help_description),
                icon = { Icon(Icons.Outlined.HelpOutline, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsHelpRoute)
                }
            )
        }
    }
}