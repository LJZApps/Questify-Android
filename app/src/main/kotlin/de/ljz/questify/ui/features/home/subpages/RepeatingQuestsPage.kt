package de.ljz.questify.ui.features.home.subpages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import de.ljz.questify.ui.features.home.HomeViewModel
import de.ljz.questify.ui.features.quests.QuestsViewModel

@Composable
fun RepeatingQuestsPage(
  modifier: Modifier = Modifier,
  viewModel: QuestsViewModel
) {
  ConstraintLayout(
    modifier = modifier.fillMaxSize()
  ) {
    val (
      text
    ) = createRefs()

    Text(
      text = "Map here",
      modifier = Modifier.constrainAs(text) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
      },
      textAlign = TextAlign.Center
    )
  }
}