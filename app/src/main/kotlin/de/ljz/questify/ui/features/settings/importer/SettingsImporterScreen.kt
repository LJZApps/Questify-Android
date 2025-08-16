package de.ljz.questify.ui.features.settings.importer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsSwitch
import de.ljz.questify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsImporterScreen(
    mainNavController: NavHostController,
    viewModel: SettingsImporterViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings_features_screen_title)) },
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
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsGroup (
                title = { Text(text = stringResource(R.string.settings_features_screen_category_quests_title)) }
            ) {
                SettingsSwitch(
                    state = uiState.questFeaturesState.fastAddingEnabled,
                    title = { Text(text = stringResource(R.string.settings_features_screen_fast_quest_creation_title)) },
                    subtitle = { Text(text = stringResource(R.string.settings_features_screen_fast_quest_creation_description)) },
                    icon = { Icon(Icons.Outlined.FlashOn, contentDescription = null) },
                    onCheckedChange = viewModel::updateFastAddingEnabled,
                )
            }
        }
    }
}