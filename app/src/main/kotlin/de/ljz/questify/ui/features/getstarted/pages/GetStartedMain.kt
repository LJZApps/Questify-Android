package de.ljz.questify.ui.features.getstarted.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.akinci.androidtemplate.ui.navigation.animations.FadeInOutAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.ljz.questify.R
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.navigation.GetStartedNavGraph
import de.ljz.questify.ui.navigation.NavGraphs
import de.ljz.questify.util.bounceClick
import io.sentry.compose.SentryTraced

@OptIn(ExperimentalComposeUiApi::class)
@GetStartedNavGraph(start = true)
@Destination(style = FadeInOutAnimation::class)
@Composable
fun GetStartedMain(
  modifier: Modifier = Modifier,
  navigator: DestinationsNavigator,
  vm: GetStartedViewModel = hiltViewModel(),
) {
  SentryTraced(tag = "get_started_main") {
    ConstraintLayout(
      modifier = modifier
        .fillMaxSize()
    ) {
      val (
        titleRef,
        buttonRef
      ) = createRefs()

      Text(
        text = "Du hast dich also dazu entschieden, dein Leben neu zu formen?",
        modifier = Modifier
          .constrainAs(titleRef) {
            top.linkTo(parent.top)
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
            bottom.linkTo(buttonRef.top)

            width = Dimension.fillToConstraints
          },
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        fontFamily = FontFamily(
          Font(R.font.tine5_regular)
        )
      )

      Button(
        onClick = {
          navigator.navigate(NavGraphs.loginAndRegister)
        },
        modifier = Modifier
          .bounceClick()
          .constrainAs(buttonRef) {
            start.linkTo(parent.start, 10.dp)
            end.linkTo(parent.end, 10.dp)
            bottom.linkTo(parent.bottom)

            width = Dimension.fillToConstraints
          }
      ) {
        Text(
          "Yes",
          fontFamily = FontFamily(
            Font(R.font.tine5_regular)
          )
        )
      }
    }
  }
}