package de.ljz.questify.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestScreen
import de.ljz.questify.ui.features.quests.create_quest.navigation.CreateQuest

fun NavGraphBuilder.questRoutes(navController: NavHostController) {
    composable<CreateQuest> {
        CreateQuestScreen(mainNavController = navController)
    }
}