package de.ljz.questify.ui.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import de.ljz.questify.R
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.home.dialogs.CreateQuestDialog
import de.ljz.questify.ui.features.home.pages.QuestTab
import io.sentry.compose.SentryTraced
import java.util.*

class HomeScreen : Screen {

  @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = navigator.getNavigatorScreenModel<HomeScreenModel>()
    val uiState = screenModel.state.collectAsState().value

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
    var showMenu by remember { mutableStateOf(false) }

    val icon = "icon"

    val annotatedString = buildAnnotatedString {
      append("0 ")

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

    QuestifyTheme(
      transparentNavBar = false
    ) {
      val tabNavigator = LocalTabNavigator.current

      SentryTraced(tag = "home_screen") {

        TabNavigator(QuestTab()) {
          Scaffold(
            topBar = {
              TopAppBar(
                title = {
                  Text(text = "Questify")
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
            },
            content = { innerPadding ->
              CurrentTab()
            },
            floatingActionButton = {
              AnimatedVisibility(
                visible = tabNavigator.current.options.index.toInt() == 0,
                enter = scaleIn(),
                exit = scaleOut(),
              ) {
                FloatingActionButton(
                  onClick = {
                    screenModel.showCreateQuestDialog()
                  }
                ) {
                  Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
              }
            },
            bottomBar = {
              NavigationBar {
              }
              /*
              NavigationBar {
                NavigationBarItem(
                  icon = { Icon(Icons.Filled.Explore, contentDescription = null) },
                  label = { Text("Quests") },
                  selected = selectedItem == 0,
                  onClick = {
                    scope.launch {
                      selectedItem = 0
                      pagerState.scrollToPage(0)
                    }
                  }
                )
                NavigationBarItem(
                  icon = { Icon(Icons.Filled.Map, contentDescription = null) },
                  label = { Text("Map") },
                  selected = selectedItem == 1,
                  onClick = {
                    scope.launch {
                      selectedItem = 1
                      pagerState.scrollToPage(1)
                    }
                  }
                )
              }
              */
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
              screenModel.hideCreateQuestDialog()
            },
            onConfirm = { _, _ ->
              // TODO
            }
          )
        }
      }
    }
  }
}