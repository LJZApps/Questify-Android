package de.ljz.questify.feature.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import de.ljz.questify.R
import de.ljz.questify.core.utils.getSerializedRouteName
import de.ljz.questify.feature.habits.presentation.screens.habits.HabitsRoute
import de.ljz.questify.feature.main.presentation.screens.main.MainUiState
import de.ljz.questify.feature.player_stats.presentation.screens.stats.StatsRoute
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestsRoute
import de.ljz.questify.feature.routines.presentation.screens.routines_overview.RoutinesOverviewRoute
import de.ljz.questify.feature.settings.presentation.screens.main.SettingsMainRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    uiState: MainUiState,
    navController: NavHostController,
    drawerState: DrawerState,
    onNavigateToSettingsScreen: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val features = listOf(
        NavigationCategory(
            title = stringResource(R.string.drawer_content_category_missions_title),
            items = listOf(
                NavigationItem(
                    title = stringResource(R.string.drawer_content_quests_title),
                    icon = painterResource(R.drawable.ic_task_alt_outlined),
                    route = QuestsRoute,
                ),
                NavigationItem(
                    title = stringResource(R.string.drawer_content_habits_title),
                    icon = painterResource(R.drawable.ic_eco_outlined),
                    featureEnabled = false,
                    route = HabitsRoute
                ),
                NavigationItem(
                    title = stringResource(R.string.drawer_content_routines_title),
                    icon = painterResource(R.drawable.ic_event_repeat_outlined),
                    route = RoutinesOverviewRoute,
                    featureEnabled = false
                ),
            )
        ),
        NavigationCategory(
            title = stringResource(R.string.drawer_content_achievements_title),
            featuresEnabled = false,
            items = listOf(
                NavigationItem(
                    title = stringResource(R.string.drawer_content_stats_title),
                    icon = painterResource(R.drawable.ic_leaderboard_outlined),
                    route = StatsRoute
                ),
                /*NavigationItem(
                    title = stringResource(R.string.drawer_content_leaderboard_title),
                    icon = Icons.Outlined.EmojiEvents,
                    route = StatsRoute,
                    featureEnabled = false
                ),*/
            )
        ),
    )

    ModalDrawerSheet(
        drawerState = drawerState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
                    .padding(vertical = 6.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onNavigateToSettingsScreen()
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = uiState.userName,
                                    style = MaterialTheme.typography.titleLarge
                                        .copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                )
                            },
                            overlineContent = {
                                Text(
                                    text = "Questify"
                                )
                            },
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (uiState.userProfilePicture.isNotEmpty()) {
                                        AsyncImage(
                                            model = uiState.userProfilePicture,
                                            contentDescription = "Profilbild",
                                            modifier = Modifier
                                                .size(40.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Profilbild",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .padding(5.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            },
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(bottom = 6.dp)
            )

            features.forEach { category ->
                if (category.featuresEnabled) {
                    if (category.showTitle) {
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                                .padding(vertical = 4.dp)
                        )
                    }

                    category.items.forEach { item ->
                        if (item.featureEnabled) {
                            NavigationDrawerItem(
                                label = { Text(text = item.title) },
                                icon = {
                                    Icon(
                                        painter = item.icon,
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
                                        Text(badge)
                                    }
                                },
                                modifier = Modifier
                                    .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.fillMaxHeight()
            )

            NavigationDrawerItem(
                label = { Text(text = "Einstellungen") },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null
                    )
                },
                selected = currentDestination?.route == getSerializedRouteName(SettingsMainRoute),
                onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    if (currentDestination?.route != getSerializedRouteName(SettingsMainRoute)) navController.navigate(
                        SettingsMainRoute
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
                    .padding(vertical = 4.dp)
            )
        }
    }
}