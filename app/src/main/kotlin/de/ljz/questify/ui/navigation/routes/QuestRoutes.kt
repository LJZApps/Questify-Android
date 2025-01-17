package de.ljz.questify.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestScreen
import de.ljz.questify.ui.features.quests.create_quest.navigation.CreateQuest
import de.ljz.questify.ui.features.quests.quest_detail.QuestDetailScreen
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail

fun NavGraphBuilder.questRoutes(navController: NavHostController) {
    composable<CreateQuest> {
        CreateQuestScreen(mainNavController = navController)
    }

    composable<QuestDetail>(
        deepLinks = listOf(
            navDeepLink<QuestDetail>(basePath = "questify://quest_detail")
        ),
    ) { backStackEntry ->
        QuestDetailScreen(navController = navController)
    }
}