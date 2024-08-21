package de.ljz.questify.ui.features.home.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity

@Composable
fun QuestPage(
  modifier: Modifier = Modifier,
  quests: List<MainQuestEntity>
) {
  ConstraintLayout (
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