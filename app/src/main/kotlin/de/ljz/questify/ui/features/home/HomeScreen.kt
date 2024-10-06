package de.ljz.questify.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import de.ljz.questify.R
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.home.dialogs.CreateQuestDialog
import de.ljz.questify.ui.navigation.home.Home
import de.ljz.questify.ui.navigation.home.HomeBottomNavGraph
import de.ljz.questify.ui.navigation.home.HomeBottomRoutes
import io.sentry.compose.SentryTraced
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.Date


@OptIn(
  ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
  ExperimentalSerializationApi::class
)
@Composable
fun HomeScreen(
  navController: NavHostController,
  viewModel: HomeScreenModel = hiltViewModel()
) {
  val bottomNavController = rememberNavController()
  val uiState = viewModel.uiState.collectAsState().value

  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  val quests = listOf(
    MainQuestEntity(
      id = 0,
      title = "test",
      points = 20,
      createdAt = Date(),
      lockDeletion = false,
      archived = false,
      done = false
    )
  )

  QuestifyTheme(
    transparentNavBar = false
  ) {
    SentryTraced(tag = "home_screen") {

      ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
          ModalDrawerSheet {
            Text("Questify", modifier = Modifier.padding(16.dp))
            HorizontalDivider()
            NavigationDrawerItem(
              label = { Text(text = "Your Quests") },
              selected = navController.currentDestination?.route == Home.serializer().descriptor.serialName,
              onClick = { /*TODO*/ }
            )
            // ...other drawer items
          }
        }
      ) {
        Scaffold(
          topBar = { TopBar(uiState.questItemCount, drawerState) },
          content = { innerPadding ->
            Box(
              modifier = Modifier
                .padding(innerPadding)
            ) {
              HomeBottomNavGraph(bottomNavController, viewModel)
            }
          },
          floatingActionButton = {
            /*AnimatedVisibility(
              visible = ,
              enter = scaleIn(),
              exit = scaleOut(),
            ) {
              FloatingActionButton(
                onClick = {
                  viewModel.showCreateQuestDialog()
                }
              ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
              }
            }*/
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
                  bottomNavController.navigate(
                    HomeBottomRoutes.TodayQuests,
                    navOptions = navOptions {
                      anim {
                        enter = 0 // Keine Enter-Animation
                        exit = 0  // Keine Exit-Animation
                        popEnter = 0 // Keine Pop-Enter-Animation
                        popExit = 0  // Keine Pop-Exit-Animation
                      }
                      popUpTo(navController.graph.startDestinationId) {
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
                  bottomNavController.navigate(
                    HomeBottomRoutes.RepeatingQuests,
                    navOptions = navOptions {
                      anim {
                        enter = 0 // Keine Enter-Animation
                        exit = 0  // Keine Exit-Animation
                        popEnter = 0 // Keine Pop-Enter-Animation
                        popExit = 0  // Keine Pop-Exit-Animation
                      }
                      popUpTo(navController.graph.startDestinationId) {
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


      if (uiState.createQuestDialogVisible) {
        CreateQuestDialog(
          onDismiss = {
            viewModel.hideCreateQuestDialog()
          },
          onConfirm = { state ->

          }
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
  questItemCount: Int,
  drawerState: DrawerState
) {
  val scope = rememberCoroutineScope()
  var showMenu by remember { mutableStateOf(false) }

  val icon = "icon"

  val annotatedString = buildAnnotatedString {
    append("$questItemCount ")

    // Placeholder für das Icon
    appendInlineContent(icon, "[icon]")
  }

  val inlineContent = mapOf(
    icon to InlineTextContent(
      // Höhe und Breite des Icons festlegen
      placeholder = Placeholder(
        width = 16.sp,
        height = 16.sp,
        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
      ),
      children = {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = null, // Beschreibung des Icons
        )
      }
    )
  )

  TopAppBar(
    title = {
      Text(
        text = "Leon",
      )
    },
    navigationIcon = {
      IconButton(onClick = {
        scope.launch {
          drawerState.apply {
            if (isClosed) open() else close()
          }
        }
      }) {
        Icon(
          imageVector = Icons.Default.Menu,
          contentDescription = "Localized description"
        )
      }
    },
    actions = {
      TextButton(
        onClick = {
          // TODO
        }
      ) {
        Text(
          text = annotatedString,
          inlineContent = inlineContent,
        )
      }
      IconButton(
        onClick = {
          showMenu = !showMenu
        }
      ) {
        Image(
          painter = painterResource(id = R.drawable.no_profile_pic),
          contentDescription = null,
          modifier = Modifier.clip(CircleShape)
        )
      }

      DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
      ) {
        DropdownMenuItem(
          onClick = { /*TODO*/ },
          text = {
            Text(text = "Connect account")
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Outlined.ManageAccounts,
              contentDescription = "Profile"
            )
          }
        )
        DropdownMenuItem(
          onClick = { /*TODO*/ },
          text = {
            Text(text = "Settings")
          },
          leadingIcon = {
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
          }
        )
      }
    }
  )
}