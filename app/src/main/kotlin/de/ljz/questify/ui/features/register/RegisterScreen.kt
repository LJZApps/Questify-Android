package de.ljz.questify.ui.features.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.akinci.androidtemplate.ui.navigation.animations.SlideHorizontallyAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.register.pages.RegisterDoneScreen
import de.ljz.questify.ui.features.register.pages.RegisterEmailScreen
import de.ljz.questify.ui.features.register.pages.RegisterPasswordScreen
import de.ljz.questify.ui.features.register.pages.RegisterUserDataScreen
import kotlinx.coroutines.launch

@Destination(style = SlideHorizontallyAnimation::class)
@Composable
fun RegisterScreen(
  navigator: DestinationsNavigator,
  modifier: Modifier = Modifier,
  vm: RegisterViewModel = hiltViewModel(),
) {
  val registerUiState by vm.uiState.collectAsState()

  val pagerState = rememberPagerState(
    initialPage = 0,
    pageCount = { registerUiState.pageCount }
  )
  val scope = rememberCoroutineScope()

  QuestifyTheme {
    Surface {
      HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false,
      ) { page ->
        when (page) {
          0 -> {
            RegisterEmailScreen(
              onEmailChange = vm::updateEmail,
              onNextPage = {
                vm.validateEmail(
                  onSuccess = {
                    scope.launch {
                      pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                  },
                  onFailure = {

                  }
                )
              },
              onBackButtonClick = {
                navigator.popBackStack()
              },
              email = registerUiState.email,
              isLoading = registerUiState.isLoading
            )
          }

          1 -> {
            RegisterPasswordScreen(
              onPasswordChange = vm::updatePassword,
              onConfirmPasswordChange = vm::updateConfirmPassword,
              onPasswordConfirm = {
                // TODO send password to server
              },
              onNextPage = {
                scope.launch {
                  pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
              },
              password = registerUiState.password,
              confirmPassword = registerUiState.confirmPassword,
            )
          }

          2 -> {
            RegisterUserDataScreen(
              onNextPage = {
                scope.launch {
                  pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
              },
              onUsernameChange = vm::updateUsername,
              onAboutMeChange = vm::updateAboutMe,
              onBirthdayChange = vm::updateBirthday,
              username = registerUiState.username,
              aboutMe = registerUiState.aboutMe,
              birthday = registerUiState.birthday
            )
          }

          3 -> {
            RegisterDoneScreen(
              onNextPage = {
                // TODO navigate to home screen
              }
            )
          }
        }
      }
    }
  }
}