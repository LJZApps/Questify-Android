package de.ljz.questify.ui.features.settings.settingshelp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SettingsHelpScreen(
    viewModel: SettingsHelpViewModel = hiltViewModel(),
    mainNavController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()


}