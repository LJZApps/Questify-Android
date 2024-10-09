package de.ljz.questify.ui.features.getstarted.subpages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.navigation.NavController
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.getstarted.components.ChooserCard
import de.ljz.questify.ui.navigation.home.Home
import io.sentry.compose.SentryTraced
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GetStartedChooserScreen(
  viewModel: GetStartedViewModel = koinViewModel(),
  navController: NavController
) {
  SentryTraced(tag = "get_started_chooser") {
    QuestifyTheme {
      Surface {
        ConstraintLayout (
          modifier = Modifier
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
                //navigator.push(LoginAndRegisterScreen())
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
                viewModel.setSetupDone()
                navController.navigate(Home)
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
}