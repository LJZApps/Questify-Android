package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.ljz.questify.feature.settings.presentation.screens.appearance.SettingsAppearanceRoute
import de.ljz.questify.feature.settings.presentation.screens.appearance.SettingsAppearanceScreen
import de.ljz.questify.feature.settings.presentation.screens.features.SettingsFeaturesRoute
import de.ljz.questify.feature.settings.presentation.screens.features.SettingsFeaturesScreen
import de.ljz.questify.feature.settings.presentation.screens.help.SettingsHelpRoute
import de.ljz.questify.feature.settings.presentation.screens.help.SettingsHelpScreen
import de.ljz.questify.feature.settings.presentation.screens.main.SettingsMainRoute
import de.ljz.questify.feature.settings.presentation.screens.main.SettingsMainScreen
import de.ljz.questify.feature.settings.presentation.screens.permissions.PermissionsScreen
import de.ljz.questify.feature.settings.presentation.screens.permissions.SettingsPermissionRoute

fun NavGraphBuilder.settingRoutes(
    navController: NavHostController
) {
    composable<SettingsMainRoute> {
        SettingsMainScreen(mainNavController = navController)
    }

    composable<SettingsPermissionRoute> { backStackEntry ->
        val arguments = backStackEntry.toRoute<SettingsPermissionRoute>()
        PermissionsScreen(
            mainNavController = navController,
            canNavigateBack = arguments.backNavigationEnabled
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