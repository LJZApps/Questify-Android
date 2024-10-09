package de.ljz.questify.ui.features.loginandregister.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import cafe.adriel.voyager.core.screen.Screen

class LoginScreen : Screen {

  @OptIn(ExperimentalComposeUiApi::class)
  @Composable
  override fun Content() {
    /*val navigator = LocalNavigator.currentOrThrow
    val screenModel = getScreenModel<LoginScreenModel>()
    val loginAndRegisterUiState = screenModel.state.collectAsState().value

    QuestifyTheme {
      SentryTraced(tag = "login_screen") {
        Surface {
          ConstraintLayout(
            modifier = Modifier
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
                screenModel.updateUsername(it)
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
              onValueChange = { screenModel.updatePassword(it) },
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
                  onClick = { screenModel.togglePasswordVisibility() }
                ) {
                  Icon(imageVector = image, description)
                }
              },
              enabled = !loginAndRegisterUiState.isLoading
            )

            Button(
              onClick = {
                screenModel.checkData {
                  //navigator.navigate(SetupAppThemeDestination)
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
                  screenModel.dismissDialog()
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
                      screenModel.dismissDialog()
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
    }*/
  }

}