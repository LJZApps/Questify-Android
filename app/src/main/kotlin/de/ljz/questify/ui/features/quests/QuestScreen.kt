package de.ljz.questify.ui.features.quests

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get
import androidx.navigation.navOptions
import de.ljz.questify.ui.components.TopBar
import de.ljz.questify.ui.navigation.home.HomeBottomNavGraph
import de.ljz.questify.ui.navigation.home.HomeBottomRoutes
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun QuestScreen(
  drawerState: DrawerState,
  viewModel: QuestsViewModel = koinViewModel()
) {
  val uiState = viewModel.uiState.collectAsState().value

  val bottomNavController = rememberNavController()
  val snackbarHostState = remember { SnackbarHostState() }

  Scaffold(
    topBar = { TopBar(uiState.userPoints, drawerState) },
    content = { innerPadding ->
      Box(
        modifier = Modifier
          .padding(innerPadding)
      ) {
        HomeBottomNavGraph(bottomNavController, viewModel)
      }
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          viewModel.addPoint()
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
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
          label = {
            Text(text = "All Quests")
          },
          icon = {
            Icon(Icons.AutoMirrored.Default.List, contentDescription = null)
          },
          selected = currentRoute == HomeBottomRoutes.TodayQuests.serializer().descriptor.serialName,
          onClick = {
            if (bottomNavController.currentDestination?.route != HomeBottomRoutes.TodayQuests.serializer().descriptor.serialName)
              bottomNavController.navigate(
                HomeBottomRoutes.TodayQuests,
                navOptions = navOptions {
                  popUpTo(bottomNavController.graph[HomeBottomRoutes.RepeatingQuests].id) {
                    inclusive = true
                    saveState = true
                  }
                  launchSingleTop = true
                  restoreState = true
                }
              )
          },
        )

        NavigationBarItem(
          label = {
            Text(text = "Repeating Quests")
          },
          icon = {
            Icon(Icons.Default.Repeat, contentDescription = null)
          },
          selected = currentRoute == HomeBottomRoutes.RepeatingQuests.serializer().descriptor.serialName,
          onClick = {
            if (bottomNavController.currentDestination?.route != HomeBottomRoutes.RepeatingQuests.serializer().descriptor.serialName)
              bottomNavController.navigate(
                HomeBottomRoutes.RepeatingQuests,
                navOptions = navOptions {
                  popUpTo(bottomNavController.graph[HomeBottomRoutes.RepeatingQuests].id) {
                    inclusive = true
                    saveState = true
                  }
                  launchSingleTop = true
                  restoreState = true
                }
              )
          },
        )
      }
    },
    snackbarHost = {
      SnackbarHost(
        hostState = snackbarHostState
      )
    }
  )
}