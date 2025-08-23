package de.ljz.questify.feature.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EnergySavingsLeaf
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Badge
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import de.ljz.questify.R
import de.ljz.questify.core.utils.getSerializedRouteName
import de.ljz.questify.feature.habits.presentation.screens.habits.HabitsRoute
import de.ljz.questify.feature.main.presentation.screens.main.MainUiState
import de.ljz.questify.feature.profile.presentation.screens.view_profile.ViewProfileRoute
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestsRoute
import de.ljz.questify.feature.settings.presentation.screens.main.SettingsMainRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
            title = stringResource(R.string.drawer_content_category_missions_title),
            items = listOf(
                NavigationItem(
                    title = stringResource(R.string.drawer_content_quests_title),
                    icon = Icons.Filled.TaskAlt,
                    route = QuestsRoute
                ),
                NavigationItem(
                    title = stringResource(R.string.drawer_content_habits_title),
                    icon = Icons.Filled.EnergySavingsLeaf,
                    route = HabitsRoute,
                    featureEnabled = false
                ),
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FilledTonalIconButton(
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            mainNavController.navigate(ViewProfileRoute)
                        },
                        shapes = IconButtonDefaults.shapes(
                            shape = MaterialShapes.Cookie9Sided.toShape()
                        )
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

                    Text(
                        text = uiState.userName,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        mainNavController.navigate(SettingsMainRoute)
                    },
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
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