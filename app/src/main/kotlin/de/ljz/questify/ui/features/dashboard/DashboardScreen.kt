package de.ljz.questify.ui.features.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.BuildConfig
import de.ljz.questify.ui.components.TopBar
import de.ljz.questify.ui.components.information_bottom_sheets.ChangelogBottomSheet
import de.ljz.questify.ui.features.dashboard.components.ChangelogComponent
import de.ljz.questify.ui.features.dashboard.components.StatsComponent
import de.ljz.questify.util.NavBarConfig
import de.ljz.questify.util.changelog.parseYamlChangelog

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
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

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    Scaffold(
        topBar = {
            TopBar(
                drawerState = drawerState,
                navController = mainNavController,
                title = "Dashboard"
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AnimatedVisibility(
                    visible = uiState.newVersionVisible,
                    enter = slideInVertically(),
                    exit = slideOutVertically()
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
                    userLevel = uiState.userLevel,
                    userXP = uiState.userXp
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )

    if (uiState.changelogVisible) {
        ChangelogBottomSheet(
            title = "Version 0.6",
            onDismiss = {
                viewModel.toggleChangelogVisibility(false)
            },
            showHideChangelog = false,
            changelogVersion = changelog
        )
    }
}