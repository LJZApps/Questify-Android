package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.ljz.questify.ui.features.settings.appearance.SettingsAppearanceRoute
import de.ljz.questify.ui.features.settings.appearance.SettingsAppearanceScreen
import de.ljz.questify.ui.features.settings.features.SettingsFeaturesRoute
import de.ljz.questify.ui.features.settings.features.SettingsFeaturesScreen
import de.ljz.questify.ui.features.settings.feedback.SettingsFeedbackRoute
import de.ljz.questify.ui.features.settings.feedback.SettingsFeedbackScreen
import de.ljz.questify.ui.features.settings.help.SettingsHelpRoute
import de.ljz.questify.ui.features.settings.help.SettingsHelpScreen
import de.ljz.questify.ui.features.settings.main.SettingsMainRoute
import de.ljz.questify.ui.features.settings.main.SettingsMainScreen
import de.ljz.questify.ui.features.settings.permissions.PermissionsScreen
import de.ljz.questify.ui.features.settings.permissions.SettingsPermissionRoute

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