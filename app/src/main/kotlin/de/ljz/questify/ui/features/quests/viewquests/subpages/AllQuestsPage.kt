package de.ljz.questify.ui.features.quests.viewquests.subpages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.components.QuestItem
import de.ljz.questify.ui.features.quests.viewquests.QuestsViewModel
import kotlinx.coroutines.launch

enum class SortType { DONE, TITLE, DIFFICULTY }

@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: QuestsViewModel,
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    var sortType by remember { mutableStateOf(SortType.DONE) }
    val listState = rememberLazyListState() // Initialisiere LazyListState für das Scrollen
    val coroutineScope = rememberCoroutineScope()

    // Sortierte Liste mit Zustand verwalten
    val sortedQuests by remember(sortType, uiState.quests) {
        derivedStateOf {
            uiState.quests.sortedWith(
                when (sortType) {
                    SortType.DONE -> compareBy { it.done }
                    SortType.TITLE -> compareBy { it.title.lowercase() }
                    SortType.DIFFICULTY -> compareBy { it.difficulty }
                }
            )
        }
    }

    // Scrollt zum Anfang, wenn das Sortierkriterium sich ändert
    LaunchedEffect(sortType) {
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Sortierung",
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Sortierauswahl
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(0, 3),
                onClick = { sortType = SortType.DONE },
                selected = sortType == SortType.DONE
            ) { Text("Fertig") }

            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(1, 3),
                onClick = { sortType = SortType.TITLE },
                selected = sortType == SortType.TITLE
            ) { Text("Titel") }

            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(2, 3),
                onClick = { sortType = SortType.DIFFICULTY },
                selected = sortType == SortType.DIFFICULTY
            ) { Text("Schwierigkeit") }
        }

        // Animierte LazyColumn mit sortierten Items
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState // Nutzt LazyListState für das Scrollen
        ) {
            items(sortedQuests, key = { it.id }) { quest ->
                QuestItem(
                    id = quest.id,
                    title = quest.title,
                    description = quest.description,
                    done = quest.done,
                    onQuestChecked = {
                        viewModel.setQuestDone(quest.id, !quest.done)
                    },
                    difficultyIcon = {
                        when (quest.difficulty) {
                            Difficulty.EASY -> EasyIcon()
                            Difficulty.MEDIUM -> MediumIcon()
                            Difficulty.HARD -> HardIcon()
                            Difficulty.EPIC -> EpicIcon()
                            else -> null
                        }
                    },
                    navController = navController,
                    modifier = Modifier
                        .animateItem() // `animateItem` für sanfte Positionsänderung
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}