package de.ljz.questify.ui.features.quests.quests_overview.sub_pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.ljz.questify.core.presentation.components.FeatureNotAvailable

@Composable
fun DailiesQuestsPage(
    modifier: Modifier = Modifier,
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
        icon = Icons.TwoTone.CalendarMonth
    )
}