package de.ljz.questify.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class TutorialStep(
    val icon: ImageVector,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialBottomSheet(
    title: String,
    tutorialSteps: List<TutorialStep>,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false },
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                state.hide()
            }
            onDismiss()
        },
        sheetState = state,
        dragHandle = {}
    ) {
        BackHandler {}

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp,),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                // Subtitle "Tutorial" mit Einfärbung
                Text(
                    text = "Tutorial",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary, // Einfärbung mit Theme-Farbe
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )

            tutorialSteps.forEach { step ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = step.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDismiss
            ) {
                Text("Verstanden")
            }
        }
    }
}