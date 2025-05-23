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
import de.ljz.questify.ui.features.settings.settings_features.SettingsFeaturesRoute
import de.ljz.questify.ui.features.settings.settings_help.SettingsHelpRoute

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
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsMenuLink(
                title = { Text(text = stringResource(R.string.settings_main_screen_features_title)) },
                subtitle = {
                    Text(
                        text = stringResource(R.string.settings_main_screen_features_description)
                    )
                },
                icon = { Icon(Icons.Outlined.Extension, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsFeaturesRoute)
                }
            )

            SettingsMenuLink(
                title = { Text(text = stringResource(R.string.settings_main_screen_appearance_title)) },
                subtitle = {
                    Text(
                        text = stringResource(R.string.settings_main_screen_appearance_description)
                    )
                },
                icon = { Icon(Icons.Outlined.ColorLens, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsAppearanceRoute)
                }
            )

            SettingsMenuLink(
                title = { Text(text = stringResource(R.string.settings_main_screen_help_title)) },
                subtitle = {
                    Text(
                        text = stringResource(R.string.settings_main_screen_help_description)
                    )
                },
                icon = { Icon(Icons.Outlined.HelpOutline, contentDescription = null) },
                onClick = {
                    mainNavController.navigate(SettingsHelpRoute)
                }
            )
        }
    }
}