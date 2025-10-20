package de.ljz.questify.feature.habits.presentation.screens.habits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.ljz.questify.feature.habits.presentation.components.HabitItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HabitsScreen(
    viewModel: HabitsViewModel = hiltViewModel(),
    onNavigateToCreateHabitScreen: () -> Unit,
    onToggleDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HabitsScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is HabitsUiEvent.OnNavigateToCreateHabitScreen -> onNavigateToCreateHabitScreen()
                is HabitsUiEvent.OnToggleDrawer -> onToggleDrawer()

                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HabitsScreen(
    uiState: HabitsUiState,
    onUiEvent: (HabitsUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Gewohnheiten",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent(HabitsUiEvent.OnToggleDrawer)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                val options = listOf("Täglich", "Wöchentlich", "Monatlich")
                var selectedIndex by remember { mutableIntStateOf(0) }

                PrimaryTabRow(
                    selectedTabIndex = selectedIndex
                ) {
                    options.forEachIndexed { index, tab ->
                        val isSelected = selectedIndex == index
                        Tab(
                            selected = isSelected,
                            onClick = {
                                selectedIndex = index
                            },
                            text = {
                                Text(
                                    text = tab,
                                    color = if (selectedIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                }

                if (uiState.habits.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.Eco,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = MaterialShapes.Pill.toShape()
                                )
                                .padding(16.dp)
                                .size(64.dp)
                        )
                        Text(
                            text = "Noch keine Gewohnheiten angelegt."
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(
                            items = uiState.habits,
                            key = { index, habit ->
                                habit.id
                            }
                        ) { index, habit ->
                            HabitItem(
                                title = habit.title,
                                notes = habit.notes,
                                type = habit.type,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = if (index == 0) 8.dp else 1.dp,
                                        bottom = 1.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    ),
                                onClick = {
                                    onUiEvent(HabitsUiEvent.OnIncrease(id = habit.id))
                                },
                                positiveCounter = 2,
                                negativeCounter = 10,
                                shape = RoundedCornerShape(
                                    topStart = if (index == 0) 16.dp else 4.dp,
                                    topEnd = if (index == 0) 16.dp else 4.dp,
                                    bottomStart = if (index == uiState.habits.lastIndex) 16.dp else 4.dp,
                                    bottomEnd = if (index == uiState.habits.lastIndex) 16.dp else 4.dp
                                ),
                                onIncrease = {},
                                onDecrease = { }
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onUiEvent(HabitsUiEvent.OnNavigateToCreateHabitScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        }
    )
}