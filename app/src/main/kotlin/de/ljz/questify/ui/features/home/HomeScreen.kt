package de.ljz.questify.ui.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import de.ljz.questify.R
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.home.pages.MapPage
import de.ljz.questify.ui.features.home.pages.QuestPage
import de.ljz.questify.ui.navigation.HomeNavGraph
import io.sentry.compose.SentryTraced
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
  navigator: NavController,
  vm: HomeViewModel = hiltViewModel()
) {
  val pagerState = rememberPagerState(pageCount = { 2 })
  var selectedItem by remember{ mutableIntStateOf(0) }
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

  val navigationItems = listOf(
    mapOf(
      "icon" to Icons.AutoMirrored.Outlined.List,
      "label" to "Quests",
      "page" to QuestPage(quests = quests)
    ),
    mapOf(
      "icon" to Icons.Outlined.Map,
      "label" to "Map",
      "page" to MapPage()
    ),
  )

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

  QuestifyTheme (
    transparentNavBar = false
  ) {
    SentryTraced(tag = "home_screen") {
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
                    Text(text = "My profile")
                  },
                  leadingIcon = {
                    Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Profile")
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
          HorizontalPager(
            state = pagerState,
            modifier = Modifier
              .padding(innerPadding)
              .fillMaxSize(),
            userScrollEnabled = false
          ) { page ->
            when (page) {
              0 -> {
                QuestPage(quests = quests)
              }

              1 -> {
                MapPage()
              }
            }
          }
        },
        floatingActionButton = {
          AnimatedVisibility(
            visible = selectedItem == 0,
            enter = scaleIn(),
            exit = scaleOut(),
          ) {
            FloatingActionButton(
              onClick = {
                // TODO
              }
            ) {
              Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
          }
        },
        bottomBar = {
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
        }
      )
    }
  }
}