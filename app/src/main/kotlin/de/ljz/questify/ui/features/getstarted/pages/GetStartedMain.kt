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
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.navigation.home.Home
import de.ljz.questify.util.bounceClick
import io.sentry.compose.SentryTraced
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GetStartedMainScreen(
  viewModel: GetStartedViewModel = koinViewModel(),
  navController: NavHostController
) {
  SentryTraced(tag = "get_started_main") {
    QuestifyTheme {
      ConstraintLayout(
        modifier = Modifier
          .fillMaxSize()
      ) {
        val (
          titleRef,
          buttonRef,
        ) = createRefs()

        Text(
          text = "Are you ready for your next Adventure?",
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
            Font(R.font.arcade)
          )
        )

        Button(
          onClick = {
            viewModel.setSetupDone()
            navController.navigate(Home)
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
            "Yes, let's start!",
            fontFamily = FontFamily(
              Font(R.font.arcade)
            )
          )
        }
      }
    }
  }
}