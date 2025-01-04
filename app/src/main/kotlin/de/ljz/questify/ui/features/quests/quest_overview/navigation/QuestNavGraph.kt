package de.ljz.questify.ui.features.quests.quest_overview.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.quests.quest_overview.QuestOverviewViewModel
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.AllQuestsPage
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.HabitsQuestsPage
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.RoutinesQuestsPage
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.DailiesQuestsPage

@Composable
fun QuestBottomNavGraph(navController: NavHostController, mainNavController: NavHostController, viewModel: QuestOverviewViewModel) {

    NavHost(
        navController = navController,
        startDestination = QuestBottomRoutes.AllQuests,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<QuestBottomRoutes.AllQuests> {
            AllQuestsPage(viewModel = viewModel, navController = mainNavController)
        }

        composable<QuestBottomRoutes.Dailies> {
            DailiesQuestsPage(viewModel = viewModel)
        }

        composable<QuestBottomRoutes.Routines> {
            RoutinesQuestsPage(viewModel = viewModel)
        }

        composable<QuestBottomRoutes.Habits> {
            HabitsQuestsPage(viewModel = viewModel)
        }
    }
}