package de.ljz.questify.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.first_setup.FirstSetupRoute
import de.ljz.questify.ui.features.first_setup.FirstSetupScreen
import de.ljz.questify.ui.features.main.MainRoute
import de.ljz.questify.ui.features.main.MainScreen

fun NavGraphBuilder.mainRoutes(navController: NavHostController) {
    composable<FirstSetupRoute> {
        FirstSetupScreen(navController = navController)
    }

    composable<MainRoute> {
        MainScreen(mainNavController = navController)
    }
}