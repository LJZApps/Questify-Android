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
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.feature.habits.data.models.HabitType
import de.ljz.questify.feature.habits.data.models.HabitsEntity
import de.ljz.questify.feature.habits.presentation.components.HabitItem
import de.ljz.questify.feature.habits.presentation.screens.create_habit.CreateHabitRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HabitsScreen(
    drawerState: DrawerState,
    viewModel: HabitsViewModel = hiltViewModel(),
    mainNavController: NavHostController,
) {
    val scope = rememberCoroutineScope()

    val habitItems = listOf(
        HabitsEntity(
            title = "Nicht rauchen",
            notes = "Nicht mehr rauchen, um Gesundheit zu verbessern und Geld zu sparen",
            type = listOf(HabitType.NEGATIVE)
        ),
        HabitsEntity(
            title = "2L Wasser trinken",
            notes = "Mehr Wasser trinken, um Gesundheit zu verbessern",
            type = listOf(HabitType.POSITIVE)
        ),
        HabitsEntity(
            title = "Bildschirmzeit",
            notes = "Nicht mehr als 2 Stunden Bildschirmzeit am Tag verbringen",
            type = listOf(HabitType.POSITIVE, HabitType.NEGATIVE)
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gewohnheiten"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (drawerState.currentValue == DrawerValue.Closed) open() else close()
                                }
                            }
                        },
                        shapes = IconButtonDefaults.shapes()
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
                if (habitItems.isEmpty()) {
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
                            items = habitItems
                        ) { index, habit ->
                            HabitItem(
                                title = habit.title,
                                notes = habit.notes,
                                types = habit.type,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = if (index == 0) 8.dp else 1.dp,
                                        bottom = 1.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    ),
                                onClick = {

                                },
                                positiveCounter = 2,
                                negativeCounter = 10,
                                shape = RoundedCornerShape(
                                    topStart = if (index == 0) 16.dp else 4.dp,
                                    topEnd = if (index == 0) 16.dp else 4.dp,
                                    bottomStart = if (index == habitItems.lastIndex) 16.dp else 4.dp,
                                    bottomEnd = if (index == habitItems.lastIndex) 16.dp else 4.dp
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
                    mainNavController.navigate(CreateHabitRoute)
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