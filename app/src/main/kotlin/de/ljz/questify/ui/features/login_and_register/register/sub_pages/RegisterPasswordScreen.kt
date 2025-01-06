package de.ljz.questify.ui.features.login_and_register.register.sub_pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import de.ljz.questify.core.compose.UIModePreviews

@Composable
fun RegisterPasswordScreen(
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onConfirmPasswordVisibilityChange: () -> Unit,
    onNextPage: () -> Unit,
    onBackButtonClick: () -> Unit,
    password: String,
    confirmPassword: String,
    passwordError: String,
    confirmPasswordError: String,
    passwordVisible: Boolean = false,
    confirmPasswordVisible: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            iconRef, titleRef, backButtonRef, nextButtonRef, passwordTextFieldRef, confirmPasswordTextFieldRef,
        ) = createRefs()

        Icon(
            imageVector = Icons.Default.Password,
            contentDescription = null,
            modifier = Modifier
              .constrainAs(iconRef) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(parent.start, 8.dp)
              }
              .size(40.dp)
        )

        Text(
            text = "Now set your password",
            modifier = Modifier.constrainAs(titleRef) {
                start.linkTo(parent.start, 8.dp)
                top.linkTo(iconRef.bottom, 8.dp)
                end.linkTo(parent.end, 8.dp)

                width = Dimension.fillToConstraints
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier
                .constrainAs(passwordTextFieldRef) {
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(titleRef.bottom, 16.dp)

                    width = Dimension.fillToConstraints
                },
            label = {
                Text(text = "Password")
            },
            isError = passwordError.isNotEmpty(),
            supportingText = {
                AnimatedVisibility(
                    visible = passwordError.isNotEmpty(),
                    enter = slideInVertically(
                        // Slide in from top
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 250),
                    ),
                    exit = slideOutVertically(
                        // Slide out to top
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 250)
                    )
                ) {
                    Text(text = passwordError)
                }
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                IconButton(
                    onClick = onPasswordVisibilityChange
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = passwordError.isNotEmpty(),
                    enter = scaleIn(animationSpec = tween(250)),
                    exit = scaleOut(animationSpec = tween(250))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Error,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier
                .constrainAs(confirmPasswordTextFieldRef) {
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(passwordTextFieldRef.bottom, 4.dp)

                    width = Dimension.fillToConstraints
                },
            label = {
                Text(text = "Confirm password")
            },
            isError = confirmPasswordError.isNotEmpty(),
            supportingText = {
                AnimatedVisibility(
                    visible = confirmPasswordError.isNotEmpty(),
                    enter = slideInVertically(
                        // Slide in from top
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 250),
                    ),
                    exit = slideOutVertically(
                        // Slide out to top
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 250)
                    )
                ) {
                    Text(text = confirmPasswordError)
                }
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                IconButton(
                    onClick = onConfirmPasswordVisibilityChange
                ) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = confirmPasswordError.isNotEmpty(),
                    enter = scaleIn(animationSpec = tween(250)),
                    exit = scaleOut(animationSpec = tween(250))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Error,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onNextPage()
                }
            ),
            singleLine = true
        )

        OutlinedButton(
            onClick = {
                onBackButtonClick()
            },
            modifier = Modifier
              .constrainAs(backButtonRef) {
                start.linkTo(parent.start, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
              }
              .imePadding()
        ) {
            Text(text = "Back")
        }

        Button(
            onClick = {
                onNextPage()
            },
            modifier = Modifier
              .constrainAs(nextButtonRef) {
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
              }
              .imePadding()
        ) {
            Text("Next")
        }
    }
}

@UIModePreviews
@Composable
fun RegisterPasswordScreenPreview() {
    RegisterPasswordScreen(
        onPasswordChange = { },
        onConfirmPasswordChange = {},
        onNextPage = { /*TODO*/ },
        onBackButtonClick = { /*TODO*/ },
        onPasswordVisibilityChange = {},
        onConfirmPasswordVisibilityChange = {},
        password = "",
        confirmPassword = "",
        passwordError = "",
        confirmPasswordError = "",
    )
}