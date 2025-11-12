package de.ljz.questify.feature.settings.presentation.screens.help

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.net.toUri
import de.ljz.questify.BuildConfig
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsMenuLink
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsHelpScreen(
    onNavigateUp: () -> Unit,
    onNavigateToOnboardingScreen: () -> Unit,
    onNavigateToSettingsPermissionScreen: () -> Unit
) {
    val context = LocalActivity.current as Activity

    SettingsHelpScreen(
        onUiEvent = { event ->
            when (event) {
                is SettingsHelpUiEvent.NavigateUp -> onNavigateUp()

                is SettingsHelpUiEvent.ShowOnboarding -> {
                    onNavigateToOnboardingScreen()
                }

                is SettingsHelpUiEvent.OnNavigateToSettingsPermissionRoute -> {
                    onNavigateToSettingsPermissionScreen()
                }

                is SettingsHelpUiEvent.SendFeedback -> {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        // "mailto:" sorgt dafür, dass nur E-Mail-Apps geöffnet werden
                        data = "mailto:".toUri()
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("lnzpk.dev@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "Feedback for Questify")
                        putExtra(
                            Intent.EXTRA_TEXT, "" +
                                    "Version name: ${BuildConfig.VERSION_NAME}\n" +
                                    "Version code: ${BuildConfig.VERSION_CODE}\n" +
                                    "Type: ${BuildConfig.BUILD_TYPE}\n\n"
                        )
                    }

                    // Prüfen, ob eine App den Intent verarbeiten kann
                    if (emailIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(emailIntent)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SettingsHelpScreen(
    onUiEvent: (SettingsHelpUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_help_screen_help_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent.invoke(SettingsHelpUiEvent.NavigateUp)
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
                .verticalScroll(rememberScrollState())
        ) {
            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_help_screen_permissions_title),
                subtitle = stringResource(R.string.settings_help_screen_permissions_description),
                icon = {
                    Icon(Icons.Outlined.VerifiedUser, contentDescription = null)
                },
                onClick = {
                    onUiEvent.invoke(SettingsHelpUiEvent.OnNavigateToSettingsPermissionRoute)
                }
            )

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_help_screen_provide_feedback_title),
                subtitle = stringResource(R.string.settings_help_screen_provide_feedback_subtitle),
                icon = {
                    Icon(Icons.Outlined.Feedback, contentDescription = null)
                },
                onClick = {
                    onUiEvent.invoke(SettingsHelpUiEvent.SendFeedback)
                }
            )

            /*ExpressiveSettingsMenuLink(
                title = "Neuigkeiten",
                subtitle = "Neues in diesem Release",
                icon = {
                    Icon(Icons.Outlined.NewReleases, contentDescription = null)
                },
                onClick = {
                    // TODO
//                    onUiEvent.invoke(SettingsHelpUiEvent.SendFeedback)
                }
            )*/

            ExpressiveSettingsMenuLink(
                title = stringResource(R.string.settings_help_screen_show_onboarding_title),
                icon = { Icon(Icons.Outlined.Explore, contentDescription = null) },
                onClick = {
                    onUiEvent.invoke(SettingsHelpUiEvent.ShowOnboarding)
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
            )
        }
    }
}