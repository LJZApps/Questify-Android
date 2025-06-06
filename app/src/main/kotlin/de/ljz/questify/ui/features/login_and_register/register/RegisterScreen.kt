package de.ljz.questify.ui.features.login_and_register.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.ui.features.login_and_register.register.sub_pages.RegisterDoneScreen
import de.ljz.questify.ui.features.login_and_register.register.sub_pages.RegisterEmailScreen
import de.ljz.questify.ui.features.login_and_register.register.sub_pages.RegisterPasswordScreen
import de.ljz.questify.ui.features.login_and_register.register.sub_pages.RegisterUserDataScreen
import de.ljz.questify.util.NavBarConfig
import io.sentry.compose.SentryTraced
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object RegisterScreenRoute

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val pagerState = rememberPagerState(pageCount = { uiState.pageCount })

    LaunchedEffect(Unit) {
       
        NavBarConfig.transparentNavBar = true
    }

    SentryTraced(tag = "register_screen") {
        Surface(modifier = Modifier.fillMaxSize()) {
            RegisterScreenPager(
                pagerState = pagerState,
                screenModel = viewModel,
                uiState = uiState,
                navController = navHostController
            )
        }
    }
}

@Composable
fun RegisterScreenPager(
    pagerState: PagerState,
    screenModel: RegisterViewModel,
    uiState: RegisterUiState,
    navController: NavHostController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val emailError by screenModel.emailError.collectAsState()
    val passwordError by screenModel.passwordError.collectAsState()
    val confirmPasswordError by screenModel.confirmPasswordError.collectAsState()

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = Modifier
    ) { page ->
        when (page) {
            0 -> RegisterEmailScreen(
                onEmailChange = screenModel::updateEmail,
                onNextPage = {
                    screenModel.validateEmail(
                        onSuccess = {
                            keyboardController?.hide()
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        },
                        onFailure = {
                            // Handle validation failure, e.g., show error message
                        }
                    )
                },
                onBackButtonClick = navController::navigateUp,
                email = uiState.email,
                error = emailError
            )

            1 -> RegisterPasswordScreen(
                onPasswordChange = screenModel::updatePassword,
                onConfirmPasswordChange = screenModel::updateConfirmPassword,
                onPasswordVisibilityChange = screenModel::togglePasswordVisibility,
                onConfirmPasswordVisibilityChange = screenModel::toggleConfirmPasswordVisibility,
                onNextPage = {
                    screenModel.validatePassword(
                        onSuccess = {
                            screenModel.hideAllPasswords()
                            keyboardController?.hide()
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        },
                        onFailure = {
                            // Handle validation failure, e.g., show error message
                        }
                    )
                },
                onBackButtonClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                },
                password = uiState.password,
                confirmPassword = uiState.confirmPassword,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                passwordVisible = uiState.passwordVisible,
                confirmPasswordVisible = uiState.confirmPasswordVisible
            )

            2 -> RegisterUserDataScreen(
                onNextPage = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
                onBackButtonClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                },
                onUsernameChange = screenModel::updateUsername,
                onAboutMeChange = screenModel::updateAboutMe,
                onDisplayNameChange = screenModel::updateDisplayName,
                username = uiState.username,
                aboutMe = uiState.aboutMe,
                displayName = uiState.displayName
            )

            3 -> RegisterDoneScreen(
                onNextPage = {
                    //navigator.push(HomeScreen())
                }
            )
        }
    }
}