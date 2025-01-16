package de.ljz.questify.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.profile.edit_profile.EditProfileScreen
import de.ljz.questify.ui.features.profile.edit_profile.navigation.EditProfileRoute
import de.ljz.questify.ui.features.profile.view_profile.ViewProfileScreen
import de.ljz.questify.ui.features.profile.view_profile.navigation.ProfileRoute

fun NavGraphBuilder.profileRoutes(navController: NavHostController) {
    composable<ProfileRoute> {
        ViewProfileScreen(navController = navController)
    }

    composable<EditProfileRoute> {
        EditProfileScreen(navController = navController)
    }
}