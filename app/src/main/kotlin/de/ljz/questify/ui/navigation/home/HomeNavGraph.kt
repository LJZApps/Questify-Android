package de.ljz.questify.ui.navigation.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.quests.viewquests.ViewQuestsViewModel
import de.ljz.questify.ui.features.quests.viewquests.navigation.QuestBottomRoutes
import de.ljz.questify.ui.features.quests.viewquests.subpages.AllQuestsPage
import de.ljz.questify.ui.features.quests.viewquests.subpages.HabitsQuestsPage
import de.ljz.questify.ui.features.quests.viewquests.subpages.RoutinesQuestsPage
import de.ljz.questify.ui.features.quests.viewquests.subpages.TodayQuestsPage

@Composable
fun HomeBottomNavGraph(navController: NavHostController, mainNavController: NavHostController, viewModel: ViewQuestsViewModel) {

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
        composable<QuestBottomRoutes.AllQuests> {
            AllQuestsPage(viewModel = viewModel, navController = mainNavController)
        }

        composable<QuestBottomRoutes.Dailies> {
            TodayQuestsPage(viewModel = viewModel)
        }

        composable<QuestBottomRoutes.Routines> {
            RoutinesQuestsPage(viewModel = viewModel)
        }

        composable<QuestBottomRoutes.Rituals> {
            HabitsQuestsPage(viewModel = viewModel)
        }
    }
}