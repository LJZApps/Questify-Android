package de.ljz.questify.ui.features.settings.settings_help

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsMenuLink
import de.ljz.questify.BuildConfig
import de.ljz.questify.R
import de.ljz.questify.ui.features.first_setup.navigation.FirstSetupRoute
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.ui.features.settings.settings_feedback.navigation.SettingsFeedbackRoute

@OptIn(ExperimentalMaterial3Api::class)
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
                    mainNavController.navigate(SettingsFeedbackRoute)
                }
            )

            SettingsMenuLink(
                title = {
                    Text("Show onboarding")
                },
                icon = { Icon(Icons.Outlined.Explore, contentDescription = null) },
                onClick = {
                    viewModel.resetOnboarding()

                    mainNavController.navigate(FirstSetupRoute) {
                        popUpTo(FirstSetupRoute) {
                            inclusive = true
                        }
                    }
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
}