package de.ljz.questify.feature.stats.presentation.screens.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun StatsScreen(
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    val scope = rememberCoroutineScope()

    StatsScreenContent(
        uiState = uiState
    ) { event ->
        when (event) {
            is StatsUiEvent.NavigateUp -> navController.navigateUp()
            is StatsUiEvent.ToggleDrawer -> {
                scope.launch {
                    drawerState.apply {
                        if (drawerState.currentValue == DrawerValue.Closed) open() else close()
                    }
                }
            }
            else -> viewModel.onUiEvent(event)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun StatsScreenContent(
    uiState: StatsUiState,
    onUiEvent: (StatsUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Werte"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent.invoke(StatsUiEvent.ToggleDrawer)
                        },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {

            }
        }
    )
}