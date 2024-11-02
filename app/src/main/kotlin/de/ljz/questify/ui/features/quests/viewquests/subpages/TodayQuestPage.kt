package de.ljz.questify.ui.features.quests.viewquests.subpages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.features.quests.viewquests.ViewQuestsViewModel

@Composable
fun TodayQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: ViewQuestsViewModel
) {
    LazyColumn (
        modifier = modifier.fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            Text(
                text = "Hier gibt es noch nichts zu sehen!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}