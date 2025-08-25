package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.feature.habits.presentation.screens.create_habit.CreateHabitRoute
import de.ljz.questify.feature.habits.presentation.screens.create_habit.CreateHabitScreen

fun NavGraphBuilder.habitRoutes(navController: NavHostController) {
    composable<CreateHabitRoute> {
        CreateHabitScreen()
    }
}