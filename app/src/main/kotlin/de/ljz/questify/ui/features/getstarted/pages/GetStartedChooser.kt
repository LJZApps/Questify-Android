package de.ljz.questify.ui.features.getstarted.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.getstarted.components.ChooserCard
import de.ljz.questify.ui.navigation.GetStartedNavGraph
import de.ljz.questify.ui.navigation.NavGraphs
import io.sentry.compose.SentryTraced

@OptIn(ExperimentalComposeUiApi::class)
@GetStartedNavGraph
@Destination
@Composable
fun GetStartedChooserScreen(
  modifier: Modifier = Modifier,
  navigator: NavController,
  vm: GetStartedViewModel = hiltViewModel(),
) {
  SentryTraced(tag = "get_started_chooser") {
    Surface {
      ConstraintLayout (
        modifier = modifier
      ) {
        val (
          title,
          buttonColumn
        ) = createRefs()

        Text(
          text = "Account stuff first or just start?",
          modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top, 12.dp)
            start.linkTo(parent.start, 12.dp)
            end.linkTo(parent.end, 12.dp)

            width = Dimension.fillToConstraints
          },
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Left
        )

        Column (
          modifier = Modifier
            .constrainAs(buttonColumn) {
              top.linkTo(title.bottom, 6.dp)
              start.linkTo(parent.start, 12.dp)
              end.linkTo(parent.end, 12.dp)
              bottom.linkTo(parent.bottom, 12.dp)

              width = Dimension.fillToConstraints
              height = Dimension.fillToConstraints
            }
            .height(IntrinsicSize.Min),
          verticalArrangement = Arrangement.SpaceBetween
        ) {
          ChooserCard(
            title = "Login or create account",
            text = "You can synchronize all your data between your other devices with an account.\nYou also have full access to our community network.",
            onClick = {
              navigator.navigate(NavGraphs.loginAndRegister)
            },
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f)
              .padding(bottom = 6.dp)
          )

          ChooserCard(
            title = "Give me my quests",
            text = "Skip the account stuff and just take me to my quests.\nPlease note that you will not be able to synchronize your quests as a result.",
            onClick = {
              // TODO
            },
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f)
              .padding(top = 6.dp)
          )
        }
      }
    }
  }
}

@UIModePreviews
@Composable
private fun GetStartedChooserScreenPreview() {
  Surface (
    modifier = Modifier.fillMaxSize()
  ) {
    GetStartedChooserScreen(navigator = rememberNavController())
  }
}