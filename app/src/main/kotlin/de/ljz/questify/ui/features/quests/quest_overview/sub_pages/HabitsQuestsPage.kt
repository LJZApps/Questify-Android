package de.ljz.questify.ui.features.quests.quest_overview.sub_pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Eco
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.ljz.questify.ui.components.FeatureNotAvailable
import de.ljz.questify.ui.features.quests.quest_overview.QuestOverviewViewModel

@Composable
fun HabitsQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: QuestOverviewViewModel
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
        icon = Icons.TwoTone.Eco
    )
}