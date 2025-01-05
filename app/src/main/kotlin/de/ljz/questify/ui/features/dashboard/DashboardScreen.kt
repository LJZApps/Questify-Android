package de.ljz.questify.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.ui.components.QuestMasterOnboarding
import de.ljz.questify.ui.components.TopBar
import de.ljz.questify.ui.components.TutorialBottomSheet
import de.ljz.questify.ui.components.TutorialStep
import de.ljz.questify.ui.features.dashboard.components.StatsComponent
import de.ljz.questify.util.NavBarConfig


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    mainNavController: NavHostController,
    drawerState: DrawerState,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

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

    // Tutorial Bottom Sheet
    if (!uiState.dashboardOnboardingDone) {
        TutorialBottomSheet(
            onDismiss = {
                viewModel.setDashboardOnboardingDone()
            },
            title = "Das Dashboard",
            tutorialSteps = listOf(
                TutorialStep(
                    icon = Icons.Default.Dashboard,
                    description = "Willkommen im Dashboard! Hier hast du alle deine Statistiken, Quests und Fortschritte auf einen Blick."
                ),
                TutorialStep(
                    icon = Icons.Default.PieChart,
                    description = "Verfolge deinen Fortschritt: Sieh dir deine abgeschlossenen Quests, täglichen Aufgaben und Routinen an."
                ),
                TutorialStep(
                    icon = Icons.Default.Settings,
                    description = "Passe dein Dashboard individuell an: Zeige die Inhalte, die dir am wichtigsten sind."
                )
            )
        )
    }
}