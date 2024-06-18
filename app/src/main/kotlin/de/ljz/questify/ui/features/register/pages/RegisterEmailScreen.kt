package de.ljz.questify.ui.features.register.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun RegisterEmailScreen(
  onEmailChange: (String) -> Unit,
  onNextPage: () -> Unit,
  onBackButtonClick: () -> Unit,
  email: String,
  isLoading: Boolean = false
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
      text = "Let's begin with your email.",
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
        }
        .verticalScroll(rememberScrollState()),
      placeholder = {
        Text(text = "Email")
      },
      label = {
        Text(text = "Email")
      },
      shape = RoundedCornerShape(16.dp),
      enabled = !isLoading
    )

    OutlinedButton(
      onClick = {
        onBackButtonClick()
      },
      modifier = Modifier.constrainAs(backButtonRef) {
        start.linkTo(parent.start, 8.dp)
        bottom.linkTo(parent.bottom, 8.dp)
      },
      enabled = !isLoading
    ) {
      Text(text = "Back")
    }

    Button(
      onClick = {
        onNextPage()
      },
      modifier = Modifier.constrainAs(nextButtonRef) {
        end.linkTo(parent.end, 8.dp)
        bottom.linkTo(parent.bottom, 8.dp)
      },
      enabled = !isLoading
    ) {
      Text("Next")
    }
  }
}