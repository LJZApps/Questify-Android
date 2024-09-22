package de.ljz.questify.ui.features.home.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.Icon
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
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity

data class QuestTab(
  val modifier: Modifier = Modifier,
  val quests: List<MainQuestEntity> = listOf()
) : Tab {

  override val options: TabOptions
    @Composable
    get() {
      val title = "Quests"
      val icon = rememberVectorPainter(Icons.Filled.Explore)

      return remember {
        TabOptions(
          index = 0u,
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
        text = "Quests here",
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