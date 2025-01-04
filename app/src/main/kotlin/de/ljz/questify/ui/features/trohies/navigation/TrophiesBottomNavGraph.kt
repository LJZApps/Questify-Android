package de.ljz.questify.ui.features.trohies.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.quests.quest_overview.QuestOverviewViewModel
import de.ljz.questify.ui.features.quests.quest_overview.navigation.QuestBottomRoutes
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.AllQuestsPage
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.HabitsQuestsPage
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.RoutinesQuestsPage
import de.ljz.questify.ui.features.quests.quest_overview.sub_pages.DailiesQuestsPage
import de.ljz.questify.ui.features.trohies.TrophiesOverviewViewModel
import de.ljz.questify.ui.features.trohies.sub_pages.AllTrophiesPage
import de.ljz.questify.ui.features.trohies.sub_pages.TrophyCategoriesPage

@Composable
fun TrophiesBottomNavGraph(navController: NavHostController, mainNavController: NavHostController, viewModel: TrophiesOverviewViewModel) {

    NavHost(
        navController = navController,
        startDestination = TrophyBottomRoutes.AllTrophiesRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<TrophyBottomRoutes.AllTrophiesRoute> {
            AllTrophiesPage(viewModel = viewModel)
        }

        composable<TrophyBottomRoutes.TrophyCategoriesRoute> {
            TrophyCategoriesPage(viewModel = viewModel)
        }
    }

}