package de.ljz.questify.feature.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
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
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import de.ljz.questify.R
import de.ljz.questify.feature.habits.presentation.screens.habits.HabitsRoute
import de.ljz.questify.feature.main.presentation.screens.main.MainUiState
import de.ljz.questify.feature.player_stats.presentation.screens.stats.StatsRoute
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestsRoute
import de.ljz.questify.feature.routines.presentation.screens.routines_overview.RoutinesOverviewRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    uiState: MainUiState,
    backStack: NavBackStack<NavKey>,
    drawerState: DrawerState,
    onNavigateToSettingsScreen: () -> Unit
) {
    val scope = rememberCoroutineScope()

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
                    featureEnabled = true,
                    route = HabitsRoute
                ),
                NavigationItem(
                    title = stringResource(R.string.drawer_content_routines_title),
                    icon = painterResource(R.drawable.ic_event_repeat_outlined),
                    route = RoutinesOverviewRoute,
                    featureEnabled = true
                ),
            )
        ),
        NavigationCategory(
            title = stringResource(R.string.drawer_content_achievements_title),
            featuresEnabled = true,
            items = listOf(
                NavigationItem(
                    title = stringResource(R.string.drawer_content_stats_title),
                    icon = painterResource(R.drawable.ic_leaderboard_outlined),
                    route = StatsRoute
                ),
                NavigationItem(
                    title = stringResource(R.string.drawer_content_leaderboard_title),
                    icon = painterResource(R.drawable.ic_trophy_outlined),
                    route = StatsRoute,
                ),
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
                                    text = stringResource(id = R.string.drawer_content_questify_title)
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
                                            contentDescription = stringResource(id = R.string.drawer_content_profile_picture_content_description),
                                            modifier = Modifier
                                                .size(40.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = stringResource(id = R.string.drawer_content_profile_picture_content_description),
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
                                selected = backStack.last() == item.route,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }

                                    val start = backStack.firstOrNull() ?: QuestsRoute

                                    if (backStack.isEmpty()) {
                                        backStack.add(start)
                                    }

                                    val existingIndex = backStack.indexOf(item.route)
                                    if (existingIndex != -1) {
                                        while (backStack.size > existingIndex + 1) {
                                            backStack.removeLastOrNull()
                                        }
                                    } else {
                                        if (backStack.lastOrNull() != item.route) {
                                            backStack.add(item.route)
                                        }
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
        }
    }
}