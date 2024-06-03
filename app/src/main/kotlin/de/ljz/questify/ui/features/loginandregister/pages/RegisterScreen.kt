package de.ljz.questify.ui.features.loginandregister.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.androidtemplate.ui.navigation.animations.SlideHorizontallyAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.ljz.questify.ui.features.loginandregister.LoginViewContract
import de.ljz.questify.ui.features.loginandregister.LoginViewModel
import de.ljz.questify.ui.navigation.LoginAndRegisterNavGraph

@LoginAndRegisterNavGraph
@Destination(style = SlideHorizontallyAnimation::class)
@Composable
fun RegisterScreen(
  navigator: DestinationsNavigator,
  modifier: Modifier = Modifier,
  vm: LoginViewModel
) {
  val uiState: LoginViewContract.State by vm.state.collectAsStateWithLifecycle()
}