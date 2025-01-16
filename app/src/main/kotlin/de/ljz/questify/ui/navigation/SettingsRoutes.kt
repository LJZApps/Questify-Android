package de.ljz.questify.ui.navigation

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
import de.ljz.questify.ui.features.settings.settings_help.SettingsHelpScreen
import de.ljz.questify.ui.features.settings.settings_help.navigation.SettingsHelp
import de.ljz.questify.ui.features.settings.settings_main.SettingsScreen
import de.ljz.questify.ui.features.settings.settings_main.navigation.Settings

fun NavGraphBuilder.settingRoutes(
    navController: NavHostController,
    permissionsVm: PermissionsViewModel,
    allPermissionsGranted: Boolean
) {
    composable<Settings> {
        SettingsScreen(mainNavController = navController)
    }

    composable<SettingsHelp> {
        SettingsHelpScreen(mainNavController = navController)
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
}