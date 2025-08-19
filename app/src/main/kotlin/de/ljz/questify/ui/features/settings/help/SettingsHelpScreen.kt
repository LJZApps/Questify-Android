package de.ljz.questify.ui.features.settings.help

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.BuildConfig
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive_settings.ExpressiveSettingsMenuLink
import de.ljz.questify.core.presentation.components.expressive_settings.ExpressiveSettingsSection
import de.ljz.questify.ui.features.first_setup.navigation.FirstSetupRoute
import de.ljz.questify.ui.features.main.navigation.MainRoute
import de.ljz.questify.ui.features.settings.feedback.navigation.SettingsFeedbackRoute
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsHelpScreen(
    mainNavController: NavHostController,
    viewModel: SettingsHelpViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_help_screen_help_title)) },
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
            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_help_screen_permissions_title),
                subtitle = stringResource(R.string.settings_help_screen_permissions_description),
                icon = {
                    Icon(Icons.Outlined.VerifiedUser, contentDescription = null)
                },
                onClick = {
                    mainNavController.navigate(SettingsPermissionRoute())
                }
            )

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_help_screen_provide_feedback_title),
                subtitle = stringResource(R.string.settings_help_screen_provide_feedback_subtitle),
                icon = {
                    Icon(Icons.Outlined.Feedback, contentDescription = null)
                },
                onClick = {
                    mainNavController.navigate(SettingsFeedbackRoute)
                }
            )

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_help_screen_show_onboarding_title),
                icon = { Icon(Icons.Outlined.Explore, contentDescription = null) },
                onClick = {
                    viewModel.resetOnboarding()

                    mainNavController.navigate(FirstSetupRoute) {
                        popUpTo(MainRoute) {
                            inclusive = true
                        }
                    }
                }
            )

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_help_screen_app_info),
                subtitle = stringResource(
                            R.string.settings_help_screen_app_info_description,
                            BuildConfig.VERSION_NAME,
                            BuildConfig.VERSION_CODE
                        ),
                icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                onClick = {}
            )
        }
    }
}