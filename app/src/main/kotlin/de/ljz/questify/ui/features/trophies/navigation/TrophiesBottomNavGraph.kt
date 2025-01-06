package de.ljz.questify.ui.features.trophies.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.trophies.TrophiesOverviewViewModel
import de.ljz.questify.ui.features.trophies.sub_pages.AllTrophiesPage
import de.ljz.questify.ui.features.trophies.sub_pages.TrophyCategoriesPage

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