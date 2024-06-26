package de.ljz.questify.ui.features.loginandregister.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.akinci.androidtemplate.ui.navigation.animations.SlideHorizontallyAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.loginandregister.LoginViewModel
import de.ljz.questify.ui.navigation.LoginAndRegisterNavGraph
import de.ljz.questify.ui.navigation.destinations.SetupAppThemeDestination
import io.sentry.compose.SentryTraced

@OptIn(ExperimentalComposeUiApi::class)
@LoginAndRegisterNavGraph
@Destination(style = SlideHorizontallyAnimation::class)
@Composable
fun LoginScreen(
  navigator: DestinationsNavigator,
  modifier: Modifier = Modifier,
  vm: LoginViewModel = hiltViewModel(),
) {
  val loginAndRegisterUiState by vm.uiState.collectAsState()

  QuestifyTheme {
    SentryTraced(tag = "login_screen") {
      Surface {
        ConstraintLayout(
          modifier = modifier
            .fillMaxSize()
        ) {
          val (
            iconRef,
            titleRef,
            usernameRef,
            passwordRef,
            loginButtonRef,
          ) = createRefs()

          Icon(
            imageVector = Icons.AutoMirrored.Filled.Login,
            contentDescription = null,
            modifier = Modifier
              .constrainAs(iconRef) {
                top.linkTo(parent.top, 12.dp)
                start.linkTo(parent.start, 12.dp)
              }
              .size(40.dp)
          )

          Text(
            text = "Login with existing account",
            modifier = Modifier.constrainAs(titleRef) {
              top.linkTo(iconRef.bottom, 6.dp)
              start.linkTo(parent.start, 12.dp)
              end.linkTo(parent.end, 12.dp)

              width = Dimension.fillToConstraints
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
          )

          OutlinedTextField(
            modifier = Modifier
              .constrainAs(usernameRef) {
                top.linkTo(titleRef.bottom, 16.dp)
                start.linkTo(parent.start, 12.dp)
                end.linkTo(parent.end, 12.dp)

                width = Dimension.fillToConstraints
              },
            value = loginAndRegisterUiState.loginState.username,
            onValueChange = {
              vm.updateUsername(it)
            },
            shape = RoundedCornerShape(16.dp),
            label = { Text(text = "Email or username") },
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Email,
              imeAction = ImeAction.Next
            ),
            singleLine = true,
            enabled = !loginAndRegisterUiState.isLoading
          )

          OutlinedTextField(
            modifier = Modifier
              .constrainAs(passwordRef) {
                top.linkTo(usernameRef.bottom, 8.dp)
                start.linkTo(parent.start, 12.dp)
                end.linkTo(parent.end, 12.dp)

                width = Dimension.fillToConstraints
              },
            value = loginAndRegisterUiState.loginState.password,
            onValueChange = { vm.updatePassword(it) },
            shape = RoundedCornerShape(16.dp),
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Password,
              imeAction = ImeAction.Done
            ),
            singleLine = true,
            visualTransformation = if (loginAndRegisterUiState.loginState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
              val image = if (loginAndRegisterUiState.loginState.passwordVisible)
                Icons.Filled.Visibility
              else Icons.Filled.VisibilityOff

              val description =
                if (loginAndRegisterUiState.registerState.passwordVisible) "Hide password" else "Show password"

              IconButton(
                onClick = { vm.togglePasswordVisibility() }
              ) {
                Icon(imageVector = image, description)
              }
            },
            enabled = !loginAndRegisterUiState.isLoading
          )

          Button(
            onClick = {
              vm.checkData {
                navigator.navigate(SetupAppThemeDestination)
              }
            },
            modifier = Modifier.constrainAs(loginButtonRef) {
              bottom.linkTo(parent.bottom, 8.dp)
              start.linkTo(parent.start, 12.dp)
              end.linkTo(parent.end, 12.dp)

              width = Dimension.fillToConstraints
            },
            enabled = !loginAndRegisterUiState.isLoading
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing)
            ) {
              AnimatedVisibility(visible = loginAndRegisterUiState.isLoading) {
                CircularProgressIndicator(
                  modifier = Modifier.size(ButtonDefaults.IconSize),
                  strokeWidth = 2.dp
                )
              }
              AnimatedVisibility(visible = !loginAndRegisterUiState.isLoading) {
                Text("Login")
              }
              AnimatedVisibility(visible = loginAndRegisterUiState.loadingText.isNotEmpty()) {
                Text(text = loginAndRegisterUiState.loadingText)
              }
            }
          }

          if (loginAndRegisterUiState.loginState.isLoginErrorShown) {
            AlertDialog(
              onDismissRequest = {
                vm.dismissDialog()
              },
              title = {
                Text(text = "Login failed")
              },
              text = {
                Text(
                  text = loginAndRegisterUiState.loginState.loginErrorMessage
                )
              },
              confirmButton = {
                TextButton(
                  onClick = {
                    vm.dismissDialog()
                  }
                ) {
                  Text(text = "Got it")
                }
              }
            )
          }
        }
      }
    }
  }
}