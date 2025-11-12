package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.feature.quests.presentation.screens.create_quest.CreateQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.edit_quest.EditQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.edit_quest.EditQuestScreen

fun NavGraphBuilder.questRoutes(navController: NavHostController) {
    composable<CreateQuestRoute> {

    }

    /*composable<QuestDetailRoute>(
        deepLinks = listOf(
            navDeepLink<QuestDetailRoute>(basePath = "questify://quest_detail")
        ),
    ) { backStackEntry ->
        QuestDetailScreen(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }*/

    composable<EditQuestRoute> {
        EditQuestScreen(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
}