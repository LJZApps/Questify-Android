package de.ljz.questify.ui.features.home

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.home.components.DrawerContent
import de.ljz.questify.ui.features.quests.QuestScreen
import de.ljz.questify.ui.features.quests.navigation.Quests
import io.sentry.compose.SentryTraced
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.androidx.compose.koinViewModel


@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSerializationApi::class
)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val homeNavHostController = rememberNavController()

    QuestifyTheme(
        transparentNavBar = false
    ) {
        SentryTraced(tag = "home_screen") {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(
                        uiState = uiState,
                        navController = homeNavHostController
                    )
                }
            ) {
                NavHost(
                    navController = homeNavHostController,
                    startDestination = Quests
                ) {
                    composable<Quests> {
                        QuestScreen(
                            drawerState = drawerState,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}