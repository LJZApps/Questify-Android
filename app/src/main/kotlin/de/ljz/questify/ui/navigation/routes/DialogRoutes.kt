package de.ljz.questify.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.dialog
import de.ljz.questify.ui.components.modals.FullscreenDialogDemo
import de.ljz.questify.ui.components.modals.FullscreenDialogDemoRoute

fun NavGraphBuilder.dialogRoutes(navController: NavHostController) {
    dialog<FullscreenDialogDemoRoute> {
        FullscreenDialogDemo(
            onDismiss = { navController.popBackStack() },
        )
    }
}