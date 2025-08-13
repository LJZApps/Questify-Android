package de.ljz.questify.ui.features.trophies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.TopBar
import de.ljz.questify.ui.features.trophies.navigation.TrophiesBottomNavGraph
import de.ljz.questify.ui.features.trophies.navigation.TrophyBottomRoutes
import de.ljz.questify.ui.navigation.BottomNavigationRoute
import de.ljz.questify.util.NavBarConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

@OptIn(
    ExperimentalMaterial3Api::class,
    InternalSerializationApi::class,
    ExperimentalSerializationApi::class
)
@Composable
fun TrophiesOverviewScreen(
    viewModel: TrophiesOverviewViewModel = hiltViewModel(),
    drawerState: DrawerState,
    mainNavController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = false
    }

    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val bottomNavRoutes = listOf(
        BottomNavigationRoute(
            stringResource(R.string.trophies_overview_screen_navigation_trophies_title),
            TrophyBottomRoutes.AllTrophiesRoute,
            Icons.Outlined.EmojiEvents
        ),
        BottomNavigationRoute(
            stringResource(R.string.trophies_overview_screen_navigation_categories_title),
            TrophyBottomRoutes.TrophyCategoriesRoute,
            Icons.Outlined.Category
        ),
    )

    Scaffold(
        topBar = {
            TopBar(
                drawerState = drawerState,
                navController = mainNavController,
                title = stringResource(R.string.trophies_overview_screen_title),
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                TrophiesBottomNavGraph(bottomNavController, mainNavController, viewModel)
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
        }
    )
}