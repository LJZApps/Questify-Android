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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import de.ljz.questify.ui.components.rowScope.TabNavigationItem
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.home.dialogs.CreateQuestDialog
import de.ljz.questify.ui.features.home.pages.MapTab
import de.ljz.questify.ui.features.home.pages.QuestTab
import io.sentry.compose.SentryTraced
import java.util.Date

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

    QuestifyTheme(
      transparentNavBar = false
    ) {
      SentryTraced(tag = "home_screen") {

        TabNavigator(QuestTab()) {
          val tabNavigator = LocalTabNavigator.current

          Scaffold(
            topBar = { TopBar() },
            content = { innerPadding -> CurrentTab() },
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
                TabNavigationItem(QuestTab(Modifier, quests))
                TabNavigationItem(MapTab(Modifier))
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
              screenModel.hideCreateQuestDialog()
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
  fun TopBar() {
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
  }
}