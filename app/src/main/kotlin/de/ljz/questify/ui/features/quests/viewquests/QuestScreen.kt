package de.ljz.questify.ui.features.quests.viewquests

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.R
import de.ljz.questify.ui.components.TopBar
import de.ljz.questify.ui.features.quests.createquest.navigation.CreateQuest
import de.ljz.questify.ui.features.quests.viewquests.navigation.BottomNavigationRoute
import de.ljz.questify.ui.features.quests.viewquests.navigation.QuestBottomRoutes
import de.ljz.questify.ui.navigation.home.HomeBottomNavGraph
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(
    ExperimentalSerializationApi::class,
    InternalSerializationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun QuestScreen(
    drawerState: DrawerState,
    viewModel: QuestsViewModel = hiltViewModel(),
    mainNavController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value

    val bottomNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val bottomNavRoutes = listOf(
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_all_quests),
            QuestBottomRoutes.AllQuests,
            Icons.AutoMirrored.Default.List
        ),
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_daily_quests),
            QuestBottomRoutes.TodayQuests,
            Icons.Default.CalendarMonth
        ),
    )

    Scaffold(
        topBar = {
            TopBar(
                uiState.userPoints,
                drawerState,
                mainNavController,
                stringResource(R.string.quest_screen_top_bar_title)
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                HomeBottomNavGraph(bottomNavController, mainNavController, viewModel)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    mainNavController.navigate(CreateQuest)
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavRoutes.forEach { bottomNavRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                bottomNavRoute.icon,
                                contentDescription = bottomNavRoute.name
                            )
                        },
                        label = { Text(bottomNavRoute.name) },
                        selected = currentDestination?.route == bottomNavRoute.route::class.serializer().descriptor.serialName,
                        onClick = {
                            bottomNavController.navigate(bottomNavRoute.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )
}