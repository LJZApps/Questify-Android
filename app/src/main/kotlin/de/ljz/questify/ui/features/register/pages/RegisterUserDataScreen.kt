package de.ljz.questify.ui.features.register.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Button
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
import de.ljz.questify.core.compose.UIModePreviews

@Composable
fun RegisterUserDataScreen(
  onNextPage: () -> Unit,
  onUsernameChange: (String) -> Unit,
  onAboutMeChange: (String) -> Unit,
  onDisplayNameChange: (String) -> Unit,
  onBackButtonClick: () -> Unit,
  displayName: String,
  username: String,
  aboutMe: String,
) {
  ConstraintLayout(
    modifier = Modifier.fillMaxSize()
  ) {
    val (
      iconRef, titleRef, subtitleRef, backButtonRef, nextButtonRef, displayNameTextFieldRef, usernameTextFieldRef, aboutMeTextFieldRef,
    ) = createRefs()

    Icon(
      imageVector = Icons.Outlined.AccountBox,
      contentDescription = null,
      modifier = Modifier
        .constrainAs(iconRef) {
          top.linkTo(parent.top, 8.dp)
          start.linkTo(parent.start, 8.dp)
        }
        .size(40.dp)
    )

    Text(
      text = "Lastly, your profile info",
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

    Text(
      text = "Everything can be edited later",
      modifier = Modifier.constrainAs(subtitleRef) {
        start.linkTo(parent.start, 8.dp)
        top.linkTo(titleRef.bottom)
        end.linkTo(parent.end, 8.dp)

        width = Dimension.fillToConstraints
      },
      fontSize = 14.sp,
      textAlign = TextAlign.Left
    )

    OutlinedTextField(
      value = displayName,
      onValueChange = onDisplayNameChange,
      modifier = Modifier
        .constrainAs(displayNameTextFieldRef) {
          start.linkTo(parent.start, 8.dp)
          end.linkTo(parent.end, 8.dp)
          top.linkTo(subtitleRef.bottom, 16.dp)

          width = Dimension.fillToConstraints
        },
      label = {
        Text(text = "Display name")
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Filled.Person,
          contentDescription = null
        )
      },
      isError = false, // TODO
      supportingText = {
        AnimatedVisibility(
          visible = false,
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
          Text(text = "Error")
        }
        AnimatedVisibility(visible = true) {
          Text(text = "How others will see you")
        }
      },
      shape = RoundedCornerShape(16.dp),
      singleLine = true,
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
      ),
    )

    OutlinedTextField(
      value = username,
      onValueChange = onUsernameChange,
      modifier = Modifier
        .constrainAs(usernameTextFieldRef) {
          start.linkTo(parent.start, 8.dp)
          end.linkTo(parent.end, 8.dp)
          top.linkTo(displayNameTextFieldRef.bottom, 8.dp)

          width = Dimension.fillToConstraints
        },
      label = {
        Text(text = "Username")
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Filled.AccountCircle,
          contentDescription = null
        )
      },
      isError = false, // TODO
      supportingText = {
        AnimatedVisibility(
          visible = false,
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
          Text(text = "Error")
        }
        AnimatedVisibility(visible = true) {
          Text(text = "How others will find you")
        }
      },
      shape = RoundedCornerShape(16.dp),
      singleLine = true,
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
      ),
    )

    OutlinedTextField(
      value = aboutMe,
      onValueChange = onAboutMeChange,
      modifier = Modifier
        .constrainAs(aboutMeTextFieldRef) {
          start.linkTo(parent.start, 8.dp)
          end.linkTo(parent.end, 8.dp)
          top.linkTo(usernameTextFieldRef.bottom)

          width = Dimension.fillToConstraints
        },
      label = {
        Text(text = "About me")
      },
      isError = false, // TODO
      supportingText = {
        Text(text = "You can leave this empty")
      },
      shape = RoundedCornerShape(16.dp),
      minLines = 3,
      maxLines = 3
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
      Text("Finish setup")
    }
  }
}

@UIModePreviews
@Composable
fun RegisterUserDataScreenPreview() {
  RegisterUserDataScreen(
    onNextPage = { /*TODO*/ },
    onUsernameChange = { },
    onAboutMeChange = {},
    onDisplayNameChange = {},
    onBackButtonClick = { /*TODO*/ },
    displayName = "",
    username = "",
    aboutMe = ""
  )
}