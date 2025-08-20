package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.feature.profile.edit_profile.EditProfileRoute
import de.ljz.questify.feature.profile.edit_profile.EditProfileScreen
import de.ljz.questify.feature.profile.view_profile.ViewProfileRoute
import de.ljz.questify.feature.profile.view_profile.ViewProfileScreen

fun NavGraphBuilder.profileRoutes(navController: NavHostController) {
    composable<ViewProfileRoute> {
        ViewProfileScreen(navController = navController)
    }

    composable<EditProfileRoute> {
        EditProfileScreen(navController = navController)
    }
}