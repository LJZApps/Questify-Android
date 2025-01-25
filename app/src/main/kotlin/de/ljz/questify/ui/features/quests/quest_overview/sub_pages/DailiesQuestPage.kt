package de.ljz.questify.ui.features.quests.quest_overview.sub_pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.twotone.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.features.quests.quest_overview.QuestOverviewViewModel

@Composable
fun DailiesQuestsPage(
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
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                imageVector = Icons.TwoTone.CalendarMonth,
                contentDescription = "Calendar",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Outlined.Block,
                contentDescription = "Blocked",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(30.dp)
                    .padding(4.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
            )
        }
        Text("Diese Funktion ist noch nicht eingebaut.")
        Spacer(modifier = Modifier.weight(1f))
    }
}