package de.ljz.questify.ui.features.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.ui.components.QuestMasterOnboarding
import de.ljz.questify.ui.components.TopBar


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    mainNavController: NavHostController,
    drawerState: DrawerState,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(
                userPoints = uiState.userPoints,
                drawerState = drawerState,
                navController = mainNavController,
                title = "Dashboard"
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Dashboard content here
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )

    // QuestMaster Onboarding Overlay
    if (!uiState.dashboardOnboardingDone) {
        QuestMasterOnboarding(
            messages = listOf(
                "Hier beginnt dein Weg durchs Dashboard – lass mich dich kurz rumführen.",
                "Hier vereinen sich deine Statistiken und Quests an einem Ort.",
                "Nutze das Dashboard, um deine Fortschritte zu erblicken und neue Ziele zu setzen."
            ),
            onDismiss = {
                viewModel.setDashboardOnboardingDone()
            }
        )
    }
}