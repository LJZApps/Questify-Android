package de.ljz.questify.feature.quests.presentation.screens.edit_quest

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun EditQuestScreen(
    viewModel: EditQuestViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    EditQuestScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is EditQuestUiEvent.OnNavigateUp -> onNavigateUp()

                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@Composable
private fun EditQuestScreen(
    uiState: EditQuestUiState,
    onUiEvent: (EditQuestUiEvent) -> Unit
) {

}