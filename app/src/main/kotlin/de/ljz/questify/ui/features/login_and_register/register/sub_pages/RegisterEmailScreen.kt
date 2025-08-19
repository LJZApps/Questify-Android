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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RegisterEmailScreen(
    onEmailChange: (String) -> Unit,
    onNextPage: () -> Unit,
    onBackButtonClick: () -> Unit,
    email: String,
    error: String
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            iconRef, titleRef, backButtonRef, nextButtonRef, emailTextFieldRef
        ) = createRefs()

        Icon(
            imageVector = Icons.Outlined.Email,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(iconRef) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(parent.start, 8.dp)
                }
                .size(40.dp)
        )

        Text(
            text = "Let's begin with your email",
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
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier
                .constrainAs(emailTextFieldRef) {
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(titleRef.bottom, 16.dp)

                    width = Dimension.fillToConstraints
                },
            label = {
                Text(text = "Email")
            },
            isError = error.isNotEmpty(),
            supportingText = {
                AnimatedVisibility(
                    visible = error.isNotEmpty(),
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
                    Text(text = error)
                }
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                error.isEmpty().let {
                    AnimatedVisibility(
                        visible = it,
                        enter = scaleIn(animationSpec = tween(250)),
                        exit = scaleOut(animationSpec = tween(250))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = null
                        )
                    }
                    AnimatedVisibility(
                        visible = !it,
                        enter = scaleIn(animationSpec = tween(250)),
                        exit = scaleOut(animationSpec = tween(250))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Error,
                            contentDescription = null
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
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
                .imePadding(),
            shapes = ButtonDefaults.shapes()
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
                .imePadding(),
            shapes = ButtonDefaults.shapes()
        ) {
            Text("Next")
        }
    }
}