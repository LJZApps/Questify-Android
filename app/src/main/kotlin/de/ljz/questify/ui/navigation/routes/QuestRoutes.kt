package de.ljz.questify.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestRoute
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestScreen
import de.ljz.questify.ui.features.quests.quest_detail.QuestDetailRoute
import de.ljz.questify.ui.features.quests.quest_detail.QuestDetailScreen

fun NavGraphBuilder.questRoutes(navController: NavHostController) {
    composable<CreateQuestRoute> {
        CreateQuestScreen(mainNavController = navController)
    }

    composable<QuestDetailRoute>(
        deepLinks = listOf(
            navDeepLink<QuestDetailRoute>(basePath = "questify://quest_detail")
        ),
    ) { backStackEntry ->
        QuestDetailScreen(navController = navController)
    }
}