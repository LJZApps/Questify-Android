package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.ljz.questify.feature.settings.appearance.SettingsAppearanceRoute
import de.ljz.questify.feature.settings.appearance.SettingsAppearanceScreen
import de.ljz.questify.feature.settings.features.SettingsFeaturesRoute
import de.ljz.questify.feature.settings.features.SettingsFeaturesScreen
import de.ljz.questify.feature.settings.feedback.SettingsFeedbackRoute
import de.ljz.questify.feature.settings.feedback.SettingsFeedbackScreen
import de.ljz.questify.feature.settings.help.SettingsHelpRoute
import de.ljz.questify.feature.settings.help.SettingsHelpScreen
import de.ljz.questify.feature.settings.main.SettingsMainRoute
import de.ljz.questify.feature.settings.main.SettingsMainScreen
import de.ljz.questify.feature.settings.permissions.PermissionsScreen
import de.ljz.questify.feature.settings.permissions.SettingsPermissionRoute

fun NavGraphBuilder.settingRoutes(
    navController: NavHostController
) {
    composable<SettingsMainRoute> {
        SettingsMainScreen(mainNavController = navController)
    }

    composable<SettingsFeedbackRoute> {
        SettingsFeedbackScreen(mainNavController = navController)
    }

    composable<SettingsPermissionRoute> { backStackEntry ->
        val arguments = backStackEntry.toRoute<SettingsPermissionRoute>()
        PermissionsScreen(
            mainNavController = navController,
            canNavigateBack = arguments.canNavigateBack
        )
    }

    composable<SettingsAppearanceRoute> {
        SettingsAppearanceScreen(mainNavController = navController)
    }

    composable<SettingsHelpRoute> {
        SettingsHelpScreen(mainNavController = navController)
    }

    composable<SettingsFeaturesRoute> {
        SettingsFeaturesScreen(mainNavController = navController)
    }
}