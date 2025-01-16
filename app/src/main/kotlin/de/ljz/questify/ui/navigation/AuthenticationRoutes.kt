package de.ljz.questify.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.ljz.questify.ui.features.login_and_register.register.RegisterScreen
import de.ljz.questify.ui.features.login_and_register.register.RegisterScreenRoute
import de.ljz.questify.ui.features.login_and_register.sub_pages.AuthChooserScreenRoute
import de.ljz.questify.ui.features.login_and_register.sub_pages.LoginAndRegisterScreen
import de.ljz.questify.ui.features.login_and_register.sub_pages.LoginScreen
import de.ljz.questify.ui.features.login_and_register.sub_pages.LoginScreenRoute

fun NavGraphBuilder.authenticationRoutes(navController: NavHostController) {
    composable<AuthChooserScreenRoute> {
        LoginAndRegisterScreen(navController)
    }

    composable<LoginScreenRoute> {
        LoginScreen(navController)
    }

    composable<RegisterScreenRoute> {
        RegisterScreen(navController)
    }
}