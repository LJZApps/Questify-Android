package de.ljz.questify.ui.features.trophies.sub_pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.EmojiEvents
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.ljz.questify.core.presentation.components.FeatureNotAvailable
import de.ljz.questify.ui.features.trophies.TrophiesOverviewViewModel

@Composable
fun AllTrophiesPage(
    modifier: Modifier = Modifier,
    viewModel: TrophiesOverviewViewModel
) {
//    LazyColumn (
//        modifier = modifier.fillMaxSize()
//            .padding(8.dp)
//    ) {
//        item {
//            Placeholder()
//        }
//    }

    FeatureNotAvailable(
        icon = Icons.TwoTone.EmojiEvents
    )
}