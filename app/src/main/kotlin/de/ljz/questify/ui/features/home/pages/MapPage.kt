package de.ljz.questify.ui.features.home.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

data class MapTab(
  val modifier: Modifier = Modifier
) : Tab {

  override val options: TabOptions
    @Composable
    get() {
      val title = "Map"
      val icon = rememberVectorPainter(Icons.Filled.Map)

      return remember {
        TabOptions(
          index = 1u,
          title = title,
          icon = icon
        )
      }
    }

  @Composable
  override fun Content() {
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

}