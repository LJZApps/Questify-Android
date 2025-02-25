package de.ljz.questify.ui.navigation.routes

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.ljz.questify.ui.features.settings.permissions.PermissionsScreen
import de.ljz.questify.ui.features.settings.permissions.PermissionsViewModel
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.ui.features.settings.settings_appearance.SettingsAppearanceRoute
import de.ljz.questify.ui.features.settings.settings_appearance.SettingsAppearanceScreen
import de.ljz.questify.ui.features.settings.settings_feedback.SettingsFeedbackScreen
import de.ljz.questify.ui.features.settings.settings_feedback.navigation.SettingsFeedbackRoute
import de.ljz.questify.ui.features.settings.settings_help.SettingsHelpRoute
import de.ljz.questify.ui.features.settings.settings_help.SettingsHelpScreen
import de.ljz.questify.ui.features.settings.settings_main.SettingsScreen
import de.ljz.questify.ui.features.settings.settings_main.navigation.SettingsRoute

fun NavGraphBuilder.settingRoutes(
    navController: NavHostController,
    permissionsVm: PermissionsViewModel,
    allPermissionsGranted: Boolean
) {
    composable<SettingsRoute> {
        SettingsScreen(mainNavController = navController)
    }

    composable<SettingsFeedbackRoute> {
        SettingsFeedbackScreen(mainNavController = navController)
    }

    composable<SettingsPermissionRoute> { backStackEntry ->
        val arguments = backStackEntry.toRoute<SettingsPermissionRoute>()
        PermissionsScreen(
            mainNavController = navController,
            viewModel = permissionsVm,
            canNavigateBack = arguments.canNavigateBack
        )

        BackHandler(enabled = !allPermissionsGranted) { }
    }

    composable<SettingsAppearanceRoute> {
        SettingsAppearanceScreen(mainNavController = navController)
    }

    composable<SettingsHelpRoute> {
        SettingsHelpScreen(mainNavController = navController)
    }
}