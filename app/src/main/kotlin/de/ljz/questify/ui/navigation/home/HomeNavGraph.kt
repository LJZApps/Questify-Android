package de.ljz.questify.ui.navigation.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.home.HomeScreenModel
import de.ljz.questify.ui.features.home.pages.AllQuestsPage
import de.ljz.questify.ui.features.home.pages.RepeatingQuestsPage

@Composable
fun HomeBottomNavGraph(navController: NavHostController, viewModel: HomeScreenModel) {

  NavHost(navController = navController, startDestination = HomeBottomRoutes.TodayQuests) {

    composable<HomeBottomRoutes.TodayQuests>(
      enterTransition = { null },
      exitTransition = { null },
      popEnterTransition = { null },
      popExitTransition = { null }
    ) {
      AllQuestsPage(viewModel = viewModel)
    }

    composable<HomeBottomRoutes.RepeatingQuests>(
      enterTransition = { null },
      exitTransition = { null },
      popEnterTransition = { null },
      popExitTransition = { null }
    ) {
      RepeatingQuestsPage(viewModel = viewModel)
    }

  }

}