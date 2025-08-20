package de.ljz.questify.feature.dashboard.presentation.screens.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.BuildConfig
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.top_bar.TopBar
import de.ljz.questify.core.utils.changelog.parseYamlChangelog
import de.ljz.questify.feature.dashboard.presentation.components.ChangelogComponent
import de.ljz.questify.feature.dashboard.presentation.components.StatsComponent
import de.ljz.questify.feature.profile.presentation.screens.view_profile.ViewProfileRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    mainNavController: NavHostController,
    drawerState: DrawerState,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    val changelog = remember {
        val inputStream = context.assets.open("changelog.yaml")
        parseYamlChangelog(inputStream)
    }.versions.find { it.version == BuildConfig.VERSION_CODE }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(
                drawerState = drawerState,
                navController = mainNavController,
                title = stringResource(R.string.dashboard_screen_title)
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AnimatedVisibility(
                    visible = uiState.newVersionVisible,
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    ChangelogComponent(
                        currentVersion = BuildConfig.VERSION_NAME,
                        changelogAvailable = (changelog != null),
                        onClick = {
                            viewModel.toggleChangelogVisibility(true)
                        },
                        onDismiss = {
                            viewModel.dismissNewVersion()
                        }
                    )
                }

                StatsComponent(
                    appUser = uiState.appUser,
                    onClick = {
                        mainNavController.navigate(ViewProfileRoute)
                    }
                )

                /*LatestQuestsComponent(
                    quests = uiState.quests
                )*/
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )
}