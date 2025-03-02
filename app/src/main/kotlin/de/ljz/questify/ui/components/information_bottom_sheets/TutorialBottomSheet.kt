package de.ljz.questify.ui.components.information_bottom_sheets

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialBottomSheet(
    title: String,
    tutorialSteps: List<TutorialStep>,
    onDismiss: (tutorialsEnabled: Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false },
    )

    val tutorialsEnabled = remember { mutableStateOf(true) }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                state.hide()
            }
            onDismiss(tutorialsEnabled.value)
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
                    text = stringResource(R.string.tutorial_bottom_sheet_title),
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
                ) {
                    Icon(
                        imageVector = step.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                onClick = { tutorialsEnabled.value = !tutorialsEnabled.value }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.tutorial_bottom_sheet_always_show),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                        Checkbox(
                            checked = tutorialsEnabled.value,
                            onCheckedChange = { tutorialsEnabled.value = it },
                        )
                    }
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDismiss(tutorialsEnabled.value)
                }
            ) {
                Text(stringResource(R.string.got_it))
            }
        }
    }
}