package de.ljz.questify.ui.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.ui.features.home.HomeUiState
import de.ljz.questify.ui.features.quests.overview.navigation.Quests
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun DrawerContent(
    uiState: HomeUiState,
    navController: NavHostController
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
              .padding(16.dp)
              .fillMaxWidth()
        ) {
            // Header mit Benutzerinformationen
            Column {
                Text(
                    text = "Welcome, Leon Zapke",
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = "Points: ${uiState.userPoints}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // Divider
            HorizontalDivider()

            // Kategorie: Quests
            Text(
                text = "Quests",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            NavigationDrawerItem(
                label = { Text(text = "Your Quests") },
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "Your Quests"
                    )
                },
                selected = navController.currentDestination?.route == Quests.serializer().descriptor.serialName,
                onClick = {
                    if (navController.currentDestination?.route != Quests.serializer().descriptor.serialName) navController.navigate(
                        Quests
                    )
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = "More coming soon!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                  .padding(vertical = 8.dp)
                  .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
                  .padding(8.dp)
                  .fillMaxWidth()
            )
        }
    }
}