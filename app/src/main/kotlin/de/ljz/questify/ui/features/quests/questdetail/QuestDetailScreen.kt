package de.ljz.questify.ui.features.quests.questdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.ui.ds.theme.QuestifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailScreen(
    id: Int,
    viewModel: QuestDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val quest = uiState.quest

    QuestifyTheme(
        transparentNavBar = true
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    actions = {
                        TextButton(
                            onClick = {
                                // TODO
                            }
                        ) {
                            Text("Löschen")
                        }
                        TextButton(
                            onClick = {
                                // TODO
                            }
                        ) {
                            Text("Speichern")
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
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

}