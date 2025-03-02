package de.ljz.questify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.R

@Composable
fun Placeholder(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.placeholder_title),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}