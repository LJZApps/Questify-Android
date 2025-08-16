package de.ljz.questify.ui.features.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import de.ljz.questify.R
import de.ljz.questify.ui.features.dashboard.navigation.DashboardRoute
import de.ljz.questify.ui.features.main.MainUiState
import de.ljz.questify.ui.features.quests.quests_overview.navigation.Quests
import de.ljz.questify.ui.features.settings.main.navigation.SettingsMainRoute
import de.ljz.questify.ui.features.trophies.navigation.TrophiesRoute
import de.ljz.questify.util.getSerializedRouteName
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    uiState: MainUiState,
    navController: NavHostController,
    mainNavController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val features = listOf(
        NavigationCategory(
            title = "",
            showTitle = false,
            items = listOf(
                NavigationItem(
                    title = "Dashboard",
                    icon = Icons.Filled.Home,
                    route = DashboardRoute,
                    featureEnabled = false
                )
            )
        ),
        NavigationCategory(
            title = stringResource(R.string.drawer_content_quests_title),
            items = listOf(
                NavigationItem(
                    title = stringResource(R.string.drawer_content_quests_navigation_title),
                    icon = Icons.AutoMirrored.Filled.List,
                    route = Quests
                ),
                NavigationItem(
                    title = stringResource(R.string.drawer_content_trophies_navigation_title),
                    icon = Icons.Filled.EmojiEvents,
                    route = TrophiesRoute,
                    featureEnabled = false
                ),
                NavigationItem(
                    title = "Fitness",
                    icon = Icons.AutoMirrored.Filled.DirectionsRun,
                    featureEnabled = false,
                    route = Quests
                ),
            )
        ),
        NavigationCategory(
            title = "Social",
            featuresEnabled = false,
            items = listOf(
                NavigationItem(
                    title = "Freunde",
                    icon = Icons.Filled.People,
                    route = Quests
                ),
                NavigationItem(
                    title = "Gilden",
                    icon = Icons.Filled.Shield,
                    route = TrophiesRoute
                )
            )
        ),
        NavigationCategory(
            title = "Village of Ventures",
            featuresEnabled = false,
            items = listOf(
                NavigationItem(
                    title = "Map",
                    icon = Icons.Filled.Map,
                    route = Quests
                ),
                NavigationItem(
                    title = "Häuser",
                    icon = Icons.Filled.House,
                    route = TrophiesRoute
                ),
                NavigationItem(
                    title = "Shop",
                    icon = Icons.Filled.ShoppingCart,
                    route = TrophiesRoute
                )
            )
        )
    )

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column {
                    Text(
                        text = uiState.userName,
                        style = MaterialTheme.typography.labelLarge,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        // XP
                        Icon(
                            imageVector = Icons.Filled.AutoGraph,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.drawe_content_xp, uiState.userXP),
                            style = MaterialTheme.typography.bodySmall
                        )

                        // Level
                        Icon(
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(
                                R.string.drawe_content_level,
                                uiState.userLevel
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )

                        // Points
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(
                                R.string.drawe_content_points,
                                uiState.userPoints
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        mainNavController.navigate(SettingsMainRoute)
                    }
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            }

            // Divider after profile information
            HorizontalDivider(
                modifier = Modifier.padding(bottom = 6.dp)
            )

            features.forEach { category ->
                if (category.featuresEnabled) {
                    if (category.showTitle) {
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    category.items.forEach { item ->
                        if (item.featureEnabled) {
                            NavigationDrawerItem(
                                label = { Text(text = item.title) },
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title
                                    )
                                },
                                selected = currentDestination?.route == getSerializedRouteName(item.route),
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    if (currentDestination?.route != getSerializedRouteName(item.route)) navController.navigate(
                                        item.route
                                    ) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                badge = {
                                    item.badge?.let { badge ->
                                        Badge {
                                            Text(badge)
                                        }
                                    }
                                },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}