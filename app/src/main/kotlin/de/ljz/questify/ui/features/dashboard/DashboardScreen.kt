package de.ljz.questify.ui.features.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.ui.components.TopBar
import de.ljz.questify.util.NavBarConfig

@Composable
fun DashboardScreen(
    mainNavController: NavHostController,
    drawerState: DrawerState,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(
                userPoints = 0,
                drawerState = drawerState,
                navController = mainNavController,
                title = "Dashboard"
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {

            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )
}