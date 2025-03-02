package de.ljz.questify.ui.components.information_bottom_sheets

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.NewReleases
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
import de.ljz.questify.BuildConfig
import de.ljz.questify.util.changelog.ChangeLogVersion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangelogBottomSheet(
    changelogVersion: ChangeLogVersion? = null,
    showHideChangelog: Boolean = true,
    onDismiss: (changelogEnabled: Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false },
    )

    val changelogEnabled = remember { mutableStateOf(true) }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                state.hide()
            }
            onDismiss(changelogEnabled.value)
        },
        sheetState = state,
        dragHandle = {}
    ) {
        BackHandler {}

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            changelogVersion?.let {
                Column {
                    Text(
                        text = changelogVersion.name,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )

                    Text(
                        text = stringResource(R.string.changelog_bottom_sheet_title),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary, // Einfärbung mit Theme-Farbe
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = stringResource(R.string.changelog_bottom_sheet_version, BuildConfig.VERSION_NAME),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )

                // New features
                changelogVersion.features?.let { features ->
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.NewReleases,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.changelog_bottom_sheet_new_features),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                            )
                        }

                        features.forEach { feature ->
                            Row(
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = feature,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                // Changes
                changelogVersion.changes?.let { changes ->
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AutoFixHigh,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.changelog_bottom_sheet_changes),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                            )
                        }

                        changes.forEach { change ->
                            Row(
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = change,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                // Bugfixes
                changelogVersion.bugfixes?.let { bugfixes ->
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.BugReport,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.changelog_bottom_sheet_bugfixes),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                            )
                        }


                        bugfixes.forEach { bugfix ->
                            Row(
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = bugfix,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                if (showHideChangelog) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        onClick = { changelogEnabled.value = !changelogEnabled.value }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.changelog_bottom_sheet_always_display),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                                Checkbox(
                                    checked = changelogEnabled.value,
                                    onCheckedChange = { changelogEnabled.value = it },
                                )
                            }
                        }
                    }
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onDismiss(changelogEnabled.value) }
                ) {
                    Text(stringResource(R.string.got_it))
                }
            }
        }
    }
}