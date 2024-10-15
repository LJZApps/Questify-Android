package de.ljz.questify.ui.features.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    mainNavController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val dynamicColorsEnabled = viewModel.dynamicColorsEnabled.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(
                        onClick = { mainNavController.popBackStack() }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            SettingsGroup(
                title = { Text(text = "Theme") },
            ) {
                SettingsSwitch(
                    state = dynamicColorsEnabled,
                    title = { Text(text = "Dynamic Colors") },
                    subtitle = { Text(text = "Paint the app in your android colors") },
                    icon = { Icon(Icons.Default.Colorize, contentDescription = null) },
                    onCheckedChange = viewModel::updateDynamicColorsEnabled,
                )
            }
        }
    }
}