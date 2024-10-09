package de.ljz.questify.ui.features.home.subpages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import de.ljz.questify.ui.features.home.HomeViewModel

@Composable
fun AllQuestsPage(
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel
) {
  Column(
    modifier = modifier.fillMaxSize()
  ) {
    Text(
      text = viewModel.uiState.collectAsState().value.questItemCount.toString(),
      textAlign = TextAlign.Center
    )

    Button(
      onClick = viewModel::increaseQuestCount,
    ) {
      Text(
        text = "Increase quest count",
        textAlign = TextAlign.Center
      )
    }
  }
}