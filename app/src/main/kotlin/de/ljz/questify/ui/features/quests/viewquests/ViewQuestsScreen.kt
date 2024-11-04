package de.ljz.questify.ui.features.quests.viewquests

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Schedule
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.R
import de.ljz.questify.ui.components.QuestMasterOnboarding
import de.ljz.questify.ui.components.TopBar
import de.ljz.questify.ui.features.quests.createquest.navigation.CreateQuest
import de.ljz.questify.ui.features.quests.viewquests.navigation.BottomNavigationRoute
import de.ljz.questify.ui.features.quests.viewquests.navigation.QuestBottomRoutes
import de.ljz.questify.ui.navigation.home.HomeBottomNavGraph
import de.ljz.questify.util.NavBarConfig
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer


@OptIn(
    ExperimentalSerializationApi::class,
    InternalSerializationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ViewQuestsScreen(
    drawerState: DrawerState,
    viewModel: ViewQuestsViewModel = hiltViewModel(),
    mainNavController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()

    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = false
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val bottomNavRoutes = listOf(
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_all_quests),
            QuestBottomRoutes.AllQuests,
            Icons.AutoMirrored.Outlined.List
        ),
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_dailies),
            QuestBottomRoutes.Dailies,
            Icons.Outlined.CalendarMonth
        ),
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_routines),
            QuestBottomRoutes.Routines,
            Icons.Outlined.Schedule
        ),
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_rituals),
            QuestBottomRoutes.Rituals,
            Icons.Outlined.Eco
        )
    )

    Scaffold(
        topBar = {
            TopBar(
                drawerState = drawerState,
                navController = mainNavController,
                title = stringResource(R.string.quest_screen_top_bar_title),
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                HomeBottomNavGraph(bottomNavController, mainNavController, viewModel)
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !(scrollBehavior.state.collapsedFraction > 0.5f),
                enter = fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 }, // Sanfteres Einblenden von der halben Höhe
                            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                        ),
                exit = fadeOut(animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)) +
                        slideOutVertically(
                            targetOffsetY = { it / 2 }, // Sanfteres Ausblenden zur halben Höhe
                            animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
                        )
            ) {
                FloatingActionButton(
                    onClick = {
                        mainNavController.navigate(CreateQuest())
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                bottomNavRoutes.forEach { bottomNavRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = bottomNavRoute.icon,
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
                        },
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

    if (!uiState.value.questOnboardingDone) {
        QuestMasterOnboarding(
            messages = listOf(
                "Dieser Ort ist deine Schmiede – hier entstehen deine Herausforderungen.",
                "Hier findest du alles für deinen Fortschritt: Quests, Dailies, Routinen und Gewohnheiten – dein Werkzeug für ein produktiveres Leben!",
                "Wähle weise, Abenteurer – jede abgeschlossene Quest bringt dich deinem Ziel näher."
            ),
            onDismiss = {
                viewModel.setOnboardingDone()
            }
        )
    }
}