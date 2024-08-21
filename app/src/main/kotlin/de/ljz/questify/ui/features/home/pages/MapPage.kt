package de.ljz.questify.ui.features.home.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Composable
fun MapPage(
  modifier: Modifier = Modifier,
) {
  ConstraintLayout (
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