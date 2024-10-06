package de.ljz.questify.ui.features.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.home.HomeScreen
import de.ljz.questify.ui.features.register.pages.RegisterDoneScreen
import de.ljz.questify.ui.features.register.pages.RegisterEmailScreen
import de.ljz.questify.ui.features.register.pages.RegisterPasswordScreen
import de.ljz.questify.ui.features.register.pages.RegisterUserDataScreen
import io.sentry.compose.SentryTraced
import kotlinx.coroutines.launch

class RegisterScreen : Screen {

  @OptIn(ExperimentalComposeUiApi::class)
  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = navigator.getNavigatorScreenModel<RegisterScreenModel>()

    val uiState = screenModel.state.collectAsState().value
    val pagerState = rememberPagerState(pageCount = { uiState.pageCount })

    QuestifyTheme {
      SentryTraced(tag = "register_screen") {
        Surface(modifier = Modifier.fillMaxSize()) {
          RegisterScreenPager(
            pagerState = pagerState,
            screenModel = screenModel,
            uiState = uiState,
            navigator = navigator
          )
        }
      }
    }
  }
}

@Composable
fun RegisterScreenPager(
  pagerState: PagerState,
  screenModel: RegisterScreenModel,
  uiState: RegisterUiState,
  navigator: Navigator
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
        onBackButtonClick = navigator::pop,
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