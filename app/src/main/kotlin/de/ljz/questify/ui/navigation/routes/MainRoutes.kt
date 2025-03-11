package de.ljz.questify.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.first_setup.FirstSetupScreen
import de.ljz.questify.ui.features.first_setup.navigation.FirstSetupRoute
import de.ljz.questify.ui.features.get_started.sub_pages.AdventureScreen
import de.ljz.questify.ui.features.get_started.sub_pages.GetStartedChooserScreen
import de.ljz.questify.ui.features.get_started.sub_pages.GetStartedMainScreen
import de.ljz.questify.ui.features.main.MainScreen
import de.ljz.questify.ui.features.main.navigation.MainRoute
import de.ljz.questify.ui.navigation.AdventureScreenRoute
import de.ljz.questify.ui.navigation.GetStartedChooser
import de.ljz.questify.ui.navigation.GetStartedMain

fun NavGraphBuilder.mainRoutes(navController: NavHostController) {
    composable<FirstSetupRoute> {
        FirstSetupScreen(navController = navController)
    }

    composable<GetStartedMain> {
        GetStartedMainScreen(navController = navController)
    }

    composable<GetStartedChooser> {
        GetStartedChooserScreen(navController = navController)
    }

    composable<MainRoute> {
        MainScreen(mainNavController = navController)
    }

    composable<AdventureScreenRoute> {
        AdventureScreen()
    }
}