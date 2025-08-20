package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.feature.first_setup.FirstSetupRoute
import de.ljz.questify.feature.first_setup.FirstSetupScreen
import de.ljz.questify.feature.main.MainRoute
import de.ljz.questify.feature.main.MainScreen

fun NavGraphBuilder.mainRoutes(navController: NavHostController) {
    composable<FirstSetupRoute> {
        FirstSetupScreen(navController = navController)
    }

    composable<MainRoute> {
        MainScreen(mainNavController = navController)
    }
}