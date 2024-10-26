package de.ljz.questify.ui.features.home

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.home.components.DrawerContent
import de.ljz.questify.ui.features.quests.viewquests.QuestScreen
import de.ljz.questify.ui.features.quests.viewquests.navigation.Quests
import de.ljz.questify.ui.navigation.ScaleTransitionDirection
import de.ljz.questify.ui.navigation.scaleIntoContainer
import de.ljz.questify.ui.navigation.scaleOutOfContainer
import de.ljz.questify.util.NavBarConfig
import io.sentry.compose.SentryTraced
import kotlinx.serialization.ExperimentalSerializationApi


@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSerializationApi::class
)
@Composable
fun HomeScreen(
    mainNavController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
       
        NavBarConfig.transparentNavBar = false
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val homeNavHostController = rememberNavController()

    QuestifyTheme {
        SentryTraced(tag = "home_screen") {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(
                        uiState = uiState,
                        navController = homeNavHostController,
                        mainNavController = mainNavController,
                        drawerState = drawerState
                    )
                }
            ) {
                NavHost(
                    navController = homeNavHostController,
                    startDestination = Quests,
                    enterTransition = {
                        scaleIntoContainer()
                    },
                    exitTransition = {
                        scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
                    },
                    popEnterTransition = {
                        scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
                    },
                    popExitTransition = {
                        scaleOutOfContainer()
                    }
                ) {
                    composable<Quests> {
                        QuestScreen(
                            drawerState = drawerState,
                            mainNavController = mainNavController
                        )
                    }
                }
            }
        }
    }
}