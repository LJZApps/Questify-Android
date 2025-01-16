package de.ljz.questify.ui.features.trophies.sub_pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.components.Placeholder
import de.ljz.questify.ui.features.trophies.TrophiesOverviewViewModel

@Composable
fun TrophyCategoriesPage(
    modifier: Modifier = Modifier,
    viewModel: TrophiesOverviewViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value.trophiesCategoriesUiState

    LazyColumn (
        modifier = modifier.fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            Placeholder()
        }
    }
}