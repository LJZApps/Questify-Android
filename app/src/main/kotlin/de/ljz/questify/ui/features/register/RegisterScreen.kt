package de.ljz.questify.ui.features.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akinci.androidtemplate.ui.navigation.animations.SlideHorizontallyAnimation
import com.ramcosta.composedestinations.annotation.Destination
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.register.pages.RegisterDoneScreen
import de.ljz.questify.ui.features.register.pages.RegisterEmailScreen
import de.ljz.questify.ui.features.register.pages.RegisterPasswordScreen
import de.ljz.questify.ui.features.register.pages.RegisterUserDataScreen
import io.sentry.compose.SentryTraced
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Destination(style = SlideHorizontallyAnimation::class)
@Composable
fun RegisterScreen(
  navigator: NavController,
  modifier: Modifier = Modifier,
  vm: RegisterViewModel = hiltViewModel(),
) {
  val registerUiState by vm.uiState.collectAsState()
  val pagerState = rememberPagerState(pageCount = { registerUiState.pageCount })
  val scope = rememberCoroutineScope()
  val emailError by vm.emailError.collectAsState()
  val passwordError by vm.passwordError.collectAsState()
  val confirmPasswordError by vm.confirmPasswordError.collectAsState()

  val keyboardController = LocalSoftwareKeyboardController.current

  BackHandler {
    if (registerUiState.backEnabled) {
      if (pagerState.currentPage > 0) {
        scope.launch {
          pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
      } else {
        navigator.popBackStack()
      }
    }
  }

  QuestifyTheme {
    SentryTraced(tag = "register_screen") {
      Surface(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
          state = pagerState,
          userScrollEnabled = false,
          modifier = Modifier
        ) { page ->
          when (page) {
            0 -> RegisterEmailScreen(
              onEmailChange = vm::updateEmail,
              onNextPage = {
                vm.validateEmail(
                  onSuccess = {
                    keyboardController?.hide()
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                  },
                  onFailure = {
                    // Handle validation failure, e.g., show error message
                  }
                )
              },
              onBackButtonClick = navigator::popBackStack,
              email = registerUiState.email,
              error = emailError
            )

            1 -> RegisterPasswordScreen(
              onPasswordChange = vm::updatePassword,
              onConfirmPasswordChange = vm::updateConfirmPassword,
              onPasswordVisibilityChange = vm::togglePasswordVisibility,
              onConfirmPasswordVisibilityChange = vm::toggleConfirmPasswordVisibility,
              onNextPage = {
                vm.validatePassword(
                  onSuccess = {
                    vm.hideAllPasswords()
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
              password = registerUiState.password,
              confirmPassword = registerUiState.confirmPassword,
              passwordError = passwordError,
              confirmPasswordError = confirmPasswordError,
              passwordVisible = registerUiState.passwordVisible,
              confirmPasswordVisible = registerUiState.confirmPasswordVisible
            )

            2 -> RegisterUserDataScreen(
              onNextPage = {
                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
              },
              onBackButtonClick = {
                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
              },
              onUsernameChange = vm::updateUsername,
              onAboutMeChange = vm::updateAboutMe,
              onDisplayNameChange = vm::updateDisplayName,
              username = registerUiState.username,
              aboutMe = registerUiState.aboutMe,
              displayName = registerUiState.displayName
            )

            3 -> RegisterDoneScreen(
              onNextPage = {
                // TODO: Navigate to home screen
              }
            )
          }
        }
      }
    }
  }
}