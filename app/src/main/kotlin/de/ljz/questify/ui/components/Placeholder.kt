package de.ljz.questify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Placeholder(
    text: String = "Ups! Hier gibt es noch nichts.",
    modifier: Modifier = Modifier
) {
    Text(
        text = "Ups! Hier gibt es noch nichts.",
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow, shape = MaterialTheme.shapes.medium)
            .padding(8.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}