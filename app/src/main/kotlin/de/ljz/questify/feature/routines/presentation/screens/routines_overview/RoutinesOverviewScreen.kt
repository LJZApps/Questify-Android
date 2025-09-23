package de.ljz.questify.feature.routines.presentation.screens.routines_overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RoutinesOverviewScreen(
    viewModel: RoutinesOverviewViewModel = hiltViewModel(),
    onToggleDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RoutinesOverviewContent(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is RoutinesOverviewUiEvent.ToggleDrawer -> onToggleDrawer()
                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoutinesOverviewContent(
    uiState: RoutinesOverviewUiState,
    onUiEvent: (RoutinesOverviewUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Routinen",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent(RoutinesOverviewUiEvent.ToggleDrawer)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Dein Tag heute",
                        style = MaterialTheme.typography.titleLarge
                            .copy(
                                fontWeight = FontWeight.Bold
                            )
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer
                                )
                                .clickable {}
                                .padding(8.dp)
                                .weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Coffee,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Column {
                                Text(
                                    text = "07:00",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Morgenroutine",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer
                                )
                                .clickable {}
                                .padding(8.dp)
                                .weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Column {
                                Text(
                                    text = "09:00",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Lern-Fokus",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}