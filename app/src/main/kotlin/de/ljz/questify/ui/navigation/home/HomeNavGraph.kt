package de.ljz.questify.ui.navigation.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.home.HomeScreenModel
import de.ljz.questify.ui.features.home.pages.AllQuestsPage
import de.ljz.questify.ui.features.home.pages.RepeatingQuestsPage

@Composable
fun HomeBottomNavGraph(navController: NavHostController, viewModel: HomeScreenModel) {

  NavHost(navController = navController,
    startDestination = HomeBottomRoutes.TodayQuests,
    enterTransition = {
      // you can change whatever you want transition
      EnterTransition.None
    },
    exitTransition = {
      // you can change whatever you want transition
      ExitTransition.None
    }) {

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