package de.ljz.questify.ui.features.trophies.sub_pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Category
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import de.ljz.questify.ui.components.FeatureNotAvailable
import de.ljz.questify.ui.features.trophies.TrophiesOverviewViewModel

@Composable
fun TrophyCategoriesPage(
    modifier: Modifier = Modifier,
    viewModel: TrophiesOverviewViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value.trophiesCategoriesUiState

//    LazyColumn (
//        modifier = modifier.fillMaxSize()
//            .padding(8.dp)
//    ) {
//        item {
//            Placeholder()
//        }
//    }

    FeatureNotAvailable(
        icon = Icons.TwoTone.Category
    )
}