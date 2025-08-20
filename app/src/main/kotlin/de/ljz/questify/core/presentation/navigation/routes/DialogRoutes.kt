package de.ljz.questify.core.presentation.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.dialog
import de.ljz.questify.core.presentation.components.modals.FullscreenDialogDemo
import de.ljz.questify.core.presentation.components.modals.FullscreenDialogDemoRoute

fun NavGraphBuilder.dialogRoutes(navController: NavHostController) {
    dialog<FullscreenDialogDemoRoute> {
        FullscreenDialogDemo(
            onDismiss = { navController.popBackStack() },
        )
    }
}