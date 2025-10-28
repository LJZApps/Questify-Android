package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.feature.first_setup.presentation.screens.first_setup.FirstSetupRoute
import de.ljz.questify.feature.first_setup.presentation.screens.first_setup.FirstSetupScreen
import de.ljz.questify.feature.habits.presentation.screens.create_habit.CreateHabitRoute
import de.ljz.questify.feature.main.presentation.screens.main.MainRoute
import de.ljz.questify.feature.main.presentation.screens.main.MainScreen
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.OnboardingRoute
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.OnboardingScreen
import de.ljz.questify.feature.quests.presentation.screens.create_quest.CreateQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.edit_quest.EditQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.quest_detail.QuestDetailRoute
import de.ljz.questify.feature.settings.presentation.screens.main.SettingsMainRoute
import de.ljz.questify.feature.settings.presentation.screens.permissions.SettingsPermissionRoute

fun NavGraphBuilder.mainRoutes(navController: NavHostController) {
    composable<OnboardingRoute> {
        OnboardingScreen(
            onNavigateUp = {
                navController.navigateUp()
            },
            onNavigateToMainScreen = {
                navController.navigate(MainRoute)
            }
        )
    }

    composable<FirstSetupRoute> {
        FirstSetupScreen(
            onNavigateBack = {
                navController.navigateUp()
            },
            onNavigateToMainScreen = {
                navController.navigate(MainRoute)
            }
        )
    }

    composable<MainRoute> {
        MainScreen(
            onNavigateToSettingsPermissionScreen = { backNavigationEnabled ->
                navController.navigate(SettingsPermissionRoute(backNavigationEnabled = backNavigationEnabled))
            },
            onNavigateToSettingsScreen = {
                navController.navigate(SettingsMainRoute)
            },
            onNavigateToCreateQuestScreen = { selectedList ->
                navController.navigate(
                    CreateQuestRoute(
                        selectedCategoryIndex = selectedList
                    )
                )
            },
            onNavigateToEditQuestScreen = { id ->
                navController.navigate(
                    EditQuestRoute(
                        id = id
                    )
                )
            },
            onNavigateToQuestDetailScreen = { id ->
                navController.navigate(
                    QuestDetailRoute(
                        id = id
                    )
                )
            },
            onNavigateToCreateHabitScreen = {
                navController.navigate(CreateHabitRoute)
            }
        )
    }
}