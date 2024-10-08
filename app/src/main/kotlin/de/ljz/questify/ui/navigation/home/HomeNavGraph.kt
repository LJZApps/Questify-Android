package de.ljz.questify.ui.navigation.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.home.HomeViewModel
import de.ljz.questify.ui.features.home.subpages.AllQuestsPage
import de.ljz.questify.ui.features.home.subpages.RepeatingQuestsPage

@Composable
fun HomeBottomNavGraph(navController: NavHostController, viewModel: HomeViewModel) {

  NavHost(
    navController = navController,
    startDestination = HomeBottomRoutes.TodayQuests,
    enterTransition = {
      // you can change whatever you want transition
      EnterTransition.None
    },
    exitTransition = {
      // you can change whatever you want transition
      ExitTransition.None
    }
  ) {
    composable<HomeBottomRoutes.TodayQuests> {
      AllQuestsPage(viewModel = viewModel)
    }

    composable<HomeBottomRoutes.RepeatingQuests> {
      BackHandler {
        // Spezifisches Verhalten, wenn der Benutzer zurück navigiert
        navController.popBackStack()
      }
      RepeatingQuestsPage(viewModel = viewModel)
    }
  }
}