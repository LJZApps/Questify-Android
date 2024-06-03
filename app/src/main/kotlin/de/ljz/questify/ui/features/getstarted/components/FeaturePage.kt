package de.ljz.questify.ui.features.getstarted.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.ds.theme.QuestifyTheme

@Composable
fun FeaturePage(
  modifier: Modifier = Modifier,
  title: String,
  description: String,
  onPageClick: (() -> Unit)
) {
  ConstraintLayout (
    modifier = modifier.fillMaxSize()
  ) {
    val (
      titleRef,
      descriptionRef,
      buttonRef
    ) = createRefs()

    Text(
      text = title,
      modifier = Modifier
        .constrainAs(titleRef) {
          top.linkTo(parent.top)
          start.linkTo(parent.start)
          end.linkTo(parent.end)

          width = Dimension.fillToConstraints
        }
        .padding(12.dp),
      textAlign = TextAlign.Center,
      fontSize = 20.sp,
    )

    Text(
      text = description,
      modifier = Modifier
        .constrainAs(descriptionRef) {
          top.linkTo(titleRef.bottom)
          start.linkTo(parent.start)
          end.linkTo(parent.end)

          width = Dimension.fillToConstraints
        }
        .padding(12.dp),
    )
  }
}

@Composable
@UIModePreviews
fun FeaturePagePreview() {
  QuestifyTheme {
    Surface {
      FeaturePage(
        title = "FEATURE_NAME",
        description = "LONG_FEATURE_DESCRIPTION",
        onPageClick = {},
        modifier = Modifier.fillMaxSize()
      )
    }
  }
}