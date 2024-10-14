package de.ljz.questify.ui.navigation.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.quests.QuestsViewModel
import de.ljz.questify.ui.features.quests.navigation.QuestBottomRoutes
import de.ljz.questify.ui.features.quests.subpages.AllQuestsPage
import de.ljz.questify.ui.features.quests.subpages.RepeatingQuestsPage
import de.ljz.questify.ui.features.quests.subpages.TodayQuestsPage

@Composable
fun HomeBottomNavGraph(navController: NavHostController, viewModel: QuestsViewModel) {

    NavHost(
        navController = navController,
        startDestination = QuestBottomRoutes.AllQuests,
        enterTransition = {
            // you can change whatever you want transition
            EnterTransition.None
        },
        exitTransition = {
            // you can change whatever you want transition
            ExitTransition.None
        }
    ) {
        composable<QuestBottomRoutes.TodayQuests> {
            TodayQuestsPage(viewModel = viewModel)
        }

        composable<QuestBottomRoutes.RepeatingQuests> {
            RepeatingQuestsPage(viewModel = viewModel)
        }

        composable<QuestBottomRoutes.AllQuests> {
            AllQuestsPage(viewModel = viewModel)
        }
    }
}