package de.ljz.questify.ui.features.home.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.features.home.HomeUiState
import de.ljz.questify.ui.features.quests.viewquests.navigation.Quests
import de.ljz.questify.ui.features.settings.settingsmain.navigation.Settings
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun DrawerContent(
    uiState: HomeUiState,
    navController: NavHostController,
    mainNavController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        Column(
            modifier = Modifier
              .padding(horizontal = 12.dp)
              .fillMaxWidth()
        ) {
            // Header mit Benutzerinformationen
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.drawer_content_title),
                        style = MaterialTheme.typography.labelLarge,
                    )

                    Text(
                        text = stringResource(R.string.drawer_content_subtitle, uiState.userPoints),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        mainNavController.navigate(Settings)
                    }
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            }

            // Divider
            HorizontalDivider()

            // Kategorie: Quests
            Text(
                text = stringResource(R.string.drawer_content_quests_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            NavigationDrawerItem(
                label = { Text(text = stringResource(R.string.drawer_content_quests_navigation_title)) },
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "Your Quests"
                    )
                },
                selected = navController.currentDestination?.route == Quests.serializer().descriptor.serialName,
                onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    if (navController.currentDestination?.route != Quests.serializer().descriptor.serialName) navController.navigate(
                        Quests
                    )
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = stringResource(R.string.drawer_content_more_coming_soon),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                  .padding(vertical = 8.dp)
                  .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
                  .padding(8.dp)
                  .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}