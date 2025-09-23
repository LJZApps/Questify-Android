package de.ljz.questify.feature.player_stats.presentation.screens.stats

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun StatsScreen(
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    val scope = rememberCoroutineScope()

    StatsScreenContent(
        uiState = uiState
    ) { event ->
        when (event) {
            is StatsUiEvent.NavigateUp -> navController.navigateUp()
            is StatsUiEvent.ToggleDrawer -> {
                scope.launch {
                    drawerState.apply {
                        if (drawerState.currentValue == DrawerValue.Closed) open() else close()
                    }
                }
            }

            else -> viewModel.onUiEvent(event)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun StatsScreenContent(
    uiState: StatsUiState,
    onUiEvent: (StatsUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Statistiken",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onUiEvent.invoke(StatsUiEvent.ToggleDrawer) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle Drawer"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingIndicator(
                        modifier = Modifier.size(160.dp)
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CharacterHeaderCard(
                        name = "Dein Name", // TODO: Aus AppUser holen
                        level = uiState.playerStats.level,
                        currentXp = uiState.playerStats.xp,
                        xpForNextLevel = uiState.xpForNextLevel,
                        currentHp = 0,
                        maxHp = 100
                    )

                    StatGridCard(
                        points = uiState.playerStats.points,
                        questsCompleted = uiState.questsCompleted
                    )
                }
            }
        }
    )
}

@Composable
fun CharacterHeaderCard(
    name: String,
    level: Int,
    currentXp: Int,
    xpForNextLevel: Int,
    currentHp: Int,
    maxHp: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = "Level $level",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            StatBar(
                label = "HP",
                currentValue = currentHp,
                maxValue = maxHp,
                color = Color(0xFFD32F2F)
            )

            StatBar(
                label = "XP",
                currentValue = currentXp,
                maxValue = xpForNextLevel,
                color = Color(0xFF7B1FA2)
            )
        }
    }
}

@Composable
fun StatGridCard(
    points: Int,
    questsCompleted: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatGridItem("Punkte", points.toString(), Modifier.weight(1f))
            StatGridItem("Quests", questsCompleted.toString(), Modifier.weight(1f))
        }
    }
}

@Composable
fun StatGridItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, style = MaterialTheme.typography.headlineMedium)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun StatBar(
    label: String,
    currentValue: Int,
    maxValue: Int,
    color: Color
) {
    val progress = (currentValue.toFloat() / maxValue.toFloat()).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500),
        label = ""
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$currentValue / $maxValue",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f),
            strokeCap = StrokeCap.Round
        )
    }
}